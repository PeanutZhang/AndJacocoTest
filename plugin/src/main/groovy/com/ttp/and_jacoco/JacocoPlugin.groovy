package com.ttp.and_jacoco

import com.android.build.gradle.AppExtension
import com.ttp.and_jacoco.extension.JacocoExtension
import com.ttp.and_jacoco.task.BranchDiffTask
import com.ttp.and_jacoco.util.Utils
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownTaskException

class JacocoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println 'zyh add some code ============================================================================='
        JacocoExtension jacocoExtension = project.extensions.create("jacocoCoverageConfig", JacocoExtension)

        project.configurations.all { configuration ->
            def name = configuration.name
            if (name != "implementation" && name != "compile") {
                return
            }
            //为Project加入agent依赖
//            configuration.dependencies.add(project.dependencies.create('com.ttp.jacoco:rt:0.0.5'))
        }

        def android = project.extensions.android


        if (android instanceof AppExtension) {
            JacocoTransform jacocoTransform = new JacocoTransform(project, jacocoExtension)
            android.registerTransform(jacocoTransform)
            println "zyh----------> android.registerTransform"
            // throw an exception in instant run mode
            android.applicationVariants.all { variant ->
                def variantName = variant.name.capitalize()
                try {
                    def instantRunTask = project.tasks.getByName("transformClassesWithInstantRunFor${variantName}")
                    if (instantRunTask) {
                        throw new GradleException("不支持instant run")
                    }
                } catch (UnknownTaskException e) {
                }
            }
        }

        project.afterEvaluate {//配置完成之后执行
            println "$Utils.ZTAG jacocoPlugin #apply projcet.afterEvaluate"
            android.applicationVariants.all { variant ->
                def variantName = variant.name.capitalize()
                println "zyh----------->project: $project afterEvalute,  variantName $variantName"
                if (project.tasks.findByName('generateReport') == null) {
                    println "zyh----------> tasks.findByTime('generateReport') is null "
                    BranchDiffTask branchDiffTask = project.tasks.create('generateReport', BranchDiffTask)
                    branchDiffTask.setGroup("jacoco")
                    branchDiffTask.jacocoExtension = jacocoExtension
                }
            }
        }
    }
}