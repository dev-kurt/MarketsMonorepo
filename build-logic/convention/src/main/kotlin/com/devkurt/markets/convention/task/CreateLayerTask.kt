package com.devkurt.markets.convention.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Locale
import javax.inject.Inject

/**
 * Usage:
 * ./gradlew createLayer --layer=ui:impl --feature=:coins --source-layer=ui:impl --source=:template-feature
 */
abstract class CreateLayerTask @Inject constructor(
    private val providers: ProviderFactory,
) : DefaultTask() {

    init {
        basePackage.convention(
            providers.gradleProperty("projectBasePackage")
        )
    }

    @get:Input
    @get:Option(option = "layer", description = "The layer name (e.g. data, domain:api, domain:impl, ui:api, ui:impl)")
    abstract val layerName: Property<String>

    @get:Input
    @get:Option(option = "feature", description = "The target feature location (e.g. :coins)")
    abstract val featureLocation: Property<String>

    @get:Input
    @get:Optional
    @get:Option(
        option = "source",
        description = "The source feature to copy from (default: :template-feature)"
    )
    abstract val sourceFeature: Property<String>

    @get:Input
    @get:Optional
    @get:Option(
        option = "source-layer",
        description = "The source layer to copy from (default: same as layer)"
    )
    abstract val sourceLayerName: Property<String>

    @get:InputDirectory
    abstract val rootDirectory: DirectoryProperty

    @get:Input
    @get:Optional
    @get:Option(
        option = "base-package",
        description = "The base package of the project (default: from gradle.properties)"
    )
    abstract val basePackage: Property<String>

    @TaskAction
    fun create() {
        val rootDir = rootDirectory.asFile.get()
        val layerNameValue = layerName.get()
        val locationValue = featureLocation.get()
        val sourceValue = sourceFeature.getOrElse(":template-feature").takeIf { it.isNotBlank() } ?: ":template-feature"
        val rawSourceLayer = sourceLayerName.getOrNull()
        val sourceLayerValue = if (rawSourceLayer.isNullOrBlank()) layerNameValue else rawSourceLayer

        require(basePackage.isPresent) {
            """
            ERROR: 'projectBasePackage' not found!
            Add 'projectBasePackage=com.your.project' to gradle.properties
            or pass '--base-package=com.your.project' when running the task.
            """.trimIndent()
        }

        val basePackageValue = basePackage.get()
        val packageParts = basePackageValue.split(".")

        val layerPath = layerNameValue.replace(":", "/")
        val sourceLayerPath = sourceLayerValue.replace(":", "/")

        val locationPath = locationValue.replace(":", "/").trimStart('/')
        val locationParts = locationValue.trimStart(':').split(":")

        val (locationType, featureKebab) = if (locationParts.size > 1) {
            locationParts[0] to locationParts.drop(1).joinToString("-")
        } else {
            "" to locationParts[0]
        }

        val sourcePath = sourceValue.replace(":", "/").trimStart('/')
        val sourceParts = sourceValue.trimStart(':').split(":")

        val (sourceLocationType, sourceFeatureKebab) = if (sourceParts.size > 1) {
            sourceParts[0] to sourceParts.drop(1).joinToString("-")
        } else {
            "" to sourceParts[0]
        }

        val sourceDir = File(rootDir, "$sourcePath/$sourceLayerPath")
        val targetDir = if (locationType.isEmpty()) {
            File(rootDir, "$featureKebab/$layerPath")
        } else {
            File(rootDir, "$locationType/$featureKebab/$layerPath")
        }

        val sourceLocationSnake = sourceLocationType.replace("-", "_")
        val targetLocationSnake = locationType.replace("-", "_")
        val sourceLocationCamel = if (sourceLocationType.isEmpty()) "" else kebabToPascal(sourceLocationType).replaceFirstChar { it.lowercase(Locale.ROOT) }
        val targetLocationCamel = if (locationType.isEmpty()) "" else kebabToPascal(locationType).replaceFirstChar { it.lowercase(Locale.ROOT) }

        val sourceFeatureSnake = sourceFeatureKebab.replace("-", "_")
        val targetFeatureSnake = featureKebab.replace("-", "_")
        val sourceFeaturePascal = kebabToPascal(sourceFeatureKebab)
        val targetFeaturePascal = kebabToPascal(featureKebab)
        val sourceFeatureCamel = sourceFeaturePascal.replaceFirstChar { it.lowercase(Locale.ROOT) }
        val targetFeatureCamel = targetFeaturePascal.replaceFirstChar { it.lowercase(Locale.ROOT) }

        val sourceLayerPascal = kebabToPascal(sourceLayerValue.replace(":", "-"))
        val targetLayerPascal = kebabToPascal(layerNameValue.replace(":", "-"))
        val sourceLayerCamel = sourceLayerPascal.replaceFirstChar { it.lowercase(Locale.ROOT) }
        val targetLayerCamel = targetLayerPascal.replaceFirstChar { it.lowercase(Locale.ROOT) }
        val sourceLayerSnake = sourceLayerValue.replace(":", "_").replace("-", "_")
        val targetLayerSnake = layerNameValue.replace(":", "_").replace("-", "_")

        val sourceLayerPackage = sourceLayerValue.replace(":", ".")
        val targetLayerPackage = layerNameValue.replace(":", ".")
        val sourceLayerFolder = sourceLayerValue.replace(":", "/")
        val targetLayerFolder = layerNameValue.replace(":", "/")

        logger.lifecycle("--------------------------------------------------")
        logger.lifecycle("Creating Layer: $layerNameValue")
        logger.lifecycle("Feature       : $locationValue")
        logger.lifecycle("Source        : $sourceValue/$sourceLayerValue")
        logger.lifecycle("Source Dir    : $sourceDir")
        logger.lifecycle("Target Dir    : $targetDir")
        logger.lifecycle("--------------------------------------------------")

        if (!sourceDir.exists()) {
            throw IllegalArgumentException("Source layer directory not found: $sourceDir")
        }
        if (targetDir.exists()) {
            throw IllegalArgumentException("Error: target layer already exists! ($targetDir)")
        }

        logger.lifecycle("Copying files...")
        copyDirectory(sourceDir, targetDir)

        logger.lifecycle("Processing files...")
        targetDir.walkBottomUp().forEach { file ->
            if (file.isFile) {
                processFile(
                    file,
                    basePackageValue,
                    sourceFeaturePascal, targetFeaturePascal,
                    sourceFeatureCamel, targetFeatureCamel,
                    sourceFeatureSnake, targetFeatureSnake,
                    sourceFeatureKebab, featureKebab,
                    sourceLocationSnake, targetLocationSnake,
                    sourceLocationCamel, targetLocationCamel,
                    sourceLayerPascal, targetLayerPascal,
                    sourceLayerCamel, targetLayerCamel,
                    sourceLayerSnake, targetLayerSnake,
                    sourceLayerPackage, targetLayerPackage
                )
            }
        }

        movePackageDirectories(
            targetDir,
            packageParts,
            sourceFeatureSnake, targetFeatureSnake,
            sourceLocationSnake, targetLocationSnake,
            sourceLayerFolder, targetLayerFolder
        )

        val settingsFile = File(rootDir, "settings.gradle.kts")
        if (settingsFile.exists()) {
            updateSettingsGradle(settingsFile, locationValue, locationType, layerNameValue)
        } else {
            logger.warn("settings.gradle.kts not found, skipping auto-include.")
        }

        if (layerPath != "di" && !layerPath.startsWith("di/")) {
            val diDir = File(rootDir, "$locationPath/di")
            if (diDir.exists()) {
                updateDiModule(diDir, targetLocationCamel, targetFeatureCamel, layerPath)
            } else {
                logger.warn("DI module not found at $diDir, skipping DI dependency update.")
            }
        } else {
            logger.lifecycle("Creating DI module itself, skipping self-reference dependency.")
        }

        logger.lifecycle("--------------------------------------------------")
        logger.lifecycle("✅ Layer created successfully!")
        logger.lifecycle("Run: ./gradlew $locationValue:${layerNameValue.replace("/", ":")}:assemble")
        logger.lifecycle("--------------------------------------------------")
    }

    private fun updateSettingsGradle(settingsFile: File, location: String, locationType: String, layerName: String) {
        val lines = settingsFile.readLines().toMutableList()
        val formattedLayer = layerName.replace("/", ":")
        val newInclude = "include(\"$location:$formattedLayer\")"

        if (lines.any { it.trim() == newInclude }) {
            logger.lifecycle("Include already exists in settings.gradle.kts, skipping.")
            return
        }

        val featurePrefix = "include(\"$location:"
        val featureLines = lines.mapIndexedNotNull { index, line ->
            if (line.trim().startsWith(featurePrefix)) index else null
        }

        if (featureLines.isNotEmpty()) {
            val diLine = lines.indexOfFirst { it.trim() == "include(\"$location:di\")" }
            if (diLine != -1 && featureLines.contains(diLine)) {
                lines.add(diLine, newInclude)
                logger.lifecycle("Added include before :di module of the feature")
            } else {
                val lastFeatureLineIndex = featureLines.last()
                lines.add(lastFeatureLineIndex + 1, newInclude)
                logger.lifecycle("Added include after last module of the feature")
            }
        } else {
            val locationTypePrefix = "include(\":$locationType:"
            val locationTypeLines = lines.mapIndexedNotNull { index, line ->
                if (line.trim().startsWith(locationTypePrefix)) index else null
            }

            if (locationTypeLines.isNotEmpty()) {
                val lastLocationLineIndex = locationTypeLines.last()
                lines.add(lastLocationLineIndex + 1, newInclude)
                lines.add(lastLocationLineIndex + 1, "")
                logger.lifecycle("Added new feature include at the end of $locationType group")
            } else {
                lines.add("")
                lines.add(newInclude)
                logger.lifecycle("Added include at end of settings.gradle.kts")
            }
        }

        settingsFile.writeText(lines.joinToString("\n"))
        logger.lifecycle("Updated settings.gradle.kts with: $newInclude")
    }

    private fun updateDiModule(diDir: File, locationCamel: String, featureCamel: String, layerPath: String) {
        val buildFile = File(diDir, "build.gradle.kts")
        if (!buildFile.exists()) {
            logger.warn("DI build.gradle.kts not found: $buildFile")
            return
        }

        val layerAccessor = layerPath.replace("/", ".")
        val projectPath = if (locationCamel.isEmpty()) {
            "projects.$featureCamel.$layerAccessor"
        } else {
            "projects.$locationCamel.$featureCamel.$layerAccessor"
        }
        val dependencyLine = "    implementation($projectPath)"

        val lines = buildFile.readLines().toMutableList()
        val dependenciesStartIndex = lines.indexOfFirst { it.trim().contains("dependencies {") }

        if (dependenciesStartIndex != -1) {
            var openBraces = 0
            var insertionIndex = -1

            for (i in dependenciesStartIndex until lines.size) {
                if (lines[i].contains("{")) openBraces++
                if (lines[i].contains("}")) openBraces--

                if (openBraces == 0) {
                    insertionIndex = i
                    break
                }
            }

            if (insertionIndex != -1) {
                val alreadyExists = lines.any { it.trim() == dependencyLine.trim() }
                if (!alreadyExists) {
                    lines.add(insertionIndex, dependencyLine)
                    buildFile.writeText(lines.joinToString("\n"))
                    logger.lifecycle("Added DI dependency: $dependencyLine")
                } else {
                    logger.lifecycle("DI dependency already exists, skipping.")
                }
            }
        } else {
            logger.warn("Could not find dependencies block in DI build.gradle.kts")
        }
    }

    private fun processFile(
        file: File,
        basePackage: String,
        sourceFeaturePascal: String, targetFeaturePascal: String,
        sourceFeatureCamel: String, targetFeatureCamel: String,
        sourceFeatureSnake: String, targetFeatureSnake: String,
        sourceFeatureKebab: String, targetFeatureKebab: String,
        sourceLocationSnake: String, targetLocationSnake: String,
        sourceLocationCamel: String, targetLocationCamel: String,
        sourceLayerPascal: String, targetLayerPascal: String,
        sourceLayerCamel: String, targetLayerCamel: String,
        sourceLayerSnake: String, targetLayerSnake: String,
        sourceLayerPackage: String, targetLayerPackage: String,
    ) {
        var content = file.readText()

        val sourcePackageBase = listOf(basePackage, sourceLocationSnake, sourceFeatureSnake)
            .filter { it.isNotEmpty() }.joinToString(".")
        val targetPackageBase = listOf(basePackage, targetLocationSnake, targetFeatureSnake)
            .filter { it.isNotEmpty() }.joinToString(".")

        val sourceFullPackage = "$sourcePackageBase.$sourceLayerPackage"
        val targetFullPackage = "$targetPackageBase.$targetLayerPackage"
        content = content.replace(sourceFullPackage, targetFullPackage)

        content = content.replace(sourcePackageBase, targetPackageBase)

        val sourceProjectBase = listOf("projects", sourceLocationCamel, sourceFeatureCamel)
            .filter { it.isNotEmpty() }.joinToString(".")
        val targetProjectBase = listOf("projects", targetLocationCamel, targetFeatureCamel)
            .filter { it.isNotEmpty() }.joinToString(".")

        val sourceFullProject = "$sourceProjectBase.$sourceLayerPackage"
        val targetFullProject = "$targetProjectBase.$targetLayerPackage"

        content = content.replace(sourceFullProject, targetFullProject)
        content = content.replace(sourceProjectBase, targetProjectBase)

        // \b alt çizgiyi kelime karakteri saydığı için snake_case/camelCase'de yanlış eşleşir;
        // bu yüzden özel sınırlar kullanılıyor.
        val snakeRegex = { word: String -> Regex("(?<=^|[^a-zA-Z0-9])$word(?=[^a-zA-Z0-9]|\\$)") }
        val camelRegex = { word: String -> Regex("(?<=^|[^a-zA-Z0-9])$word(?=[A-Z]|[^a-zA-Z0-9]|\\$)") }
        val pascalRegex = { word: String -> Regex("(?<=^|[^a-zA-Z0-9]|[a-z])$word(?=[A-Z]|[^a-zA-Z0-9]|\\$)") }

        if (sourceLayerPascal != targetLayerPascal) {
            content = content.replace(pascalRegex(sourceLayerPascal), targetLayerPascal)
            content = content.replace(camelRegex(sourceLayerCamel), targetLayerCamel)
            content = content.replace(snakeRegex(sourceLayerSnake), targetLayerSnake)
        }

        content = content.replace(pascalRegex(sourceFeaturePascal), targetFeaturePascal)
        content = content.replace(camelRegex(sourceFeatureCamel), targetFeatureCamel)
        content = content.replace(snakeRegex(sourceFeatureSnake), targetFeatureSnake)
        content = content.replace(snakeRegex(sourceFeatureKebab), targetFeatureKebab)

        file.writeText(content)

        var newFileName = file.name
        if (sourceLayerPascal != targetLayerPascal && newFileName.contains(sourceLayerPascal)) {
            newFileName = newFileName.replace(sourceLayerPascal, targetLayerPascal)
        }
        if (sourceFeaturePascal != targetFeaturePascal && newFileName.contains(sourceFeaturePascal)) {
            newFileName = newFileName.replace(sourceFeaturePascal, targetFeaturePascal)
        }

        if (newFileName != file.name) {
            Files.move(file.toPath(), file.toPath().resolveSibling(newFileName), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    private fun movePackageDirectories(
        targetDir: File,
        packageParts: List<String>,
        sourceFeatureSnake: String,
        targetFeatureSnake: String,
        sourceLocationSnake: String,
        targetLocationSnake: String,
        sourceLayerFolder: String,
        targetLayerFolder: String,
    ) {
        targetDir.walkBottomUp().forEach { dir ->
            if (dir.isDirectory && dir.name == packageParts.firstOrNull()) {
                var currentDir = dir
                var foundBase = true
                for (i in 1 until packageParts.size) {
                    val nextDir = File(currentDir, packageParts[i])
                    if (nextDir.exists()) {
                        currentDir = nextDir
                    } else {
                        foundBase = false
                        break
                    }
                }

                if (foundBase) {
                    val oldLocationDir = if (sourceLocationSnake.isNotEmpty()) File(currentDir, sourceLocationSnake) else currentDir
                    val oldFeatureDir = File(oldLocationDir, sourceFeatureSnake)
                    val oldLayerDir = File(oldFeatureDir, sourceLayerFolder)

                    if (oldLayerDir.exists()) {
                        val newLocationDir = if (targetLocationSnake.isNotEmpty()) File(currentDir, targetLocationSnake).also { it.mkdirs() } else currentDir
                        val newFeatureDir = File(newLocationDir, targetFeatureSnake).also { it.mkdirs() }
                        val newLayerDir = File(newFeatureDir, targetLayerFolder)

                        if (oldLayerDir.absolutePath != newLayerDir.absolutePath) {
                            newLayerDir.parentFile?.mkdirs()
                            Files.move(oldLayerDir.toPath(), newLayerDir.toPath(), StandardCopyOption.REPLACE_EXISTING)

                            cleanEmptyDirectoriesRecursively(oldFeatureDir)
                            if (sourceLocationSnake.isNotEmpty()) cleanEmptyDirectoriesRecursively(oldLocationDir)
                        }
                    }
                }
            }
        }
    }

    private fun cleanEmptyDirectoriesRecursively(dir: File) {
        if (!dir.exists() || !dir.isDirectory) return

        dir.listFiles()?.forEach { child ->
            if (child.isDirectory) {
                cleanEmptyDirectoriesRecursively(child)
            } else if (child.name == ".DS_Store") {
                child.delete()
            }
        }

        if (dir.listFiles()?.isEmpty() == true) {
            dir.delete()
        }
    }

    private fun kebabToPascal(kebab: String): String {
        return kebab.split("-").joinToString("") {
            it.replaceFirstChar { char -> char.uppercase(Locale.ROOT) }
        }
    }

    private fun copyDirectory(source: File, target: File) {
        if (!source.isDirectory) return

        if (target.canonicalPath.startsWith(source.canonicalPath)) {
            return
        }

        if (!target.exists()) {
            target.mkdirs()
        }

        source.listFiles()?.forEach { file ->
            val fileName = file.name
            if (fileName == "build" || fileName == ".gradle" || fileName == ".git" || fileName == ".DS_Store" || fileName == "docs") {
                return@forEach
            }

            val targetFile = File(target, fileName)
            if (file.isDirectory) {
                copyDirectory(file, targetFile)
            } else {
                file.copyTo(targetFile, overwrite = true)
            }
        }
    }
}
