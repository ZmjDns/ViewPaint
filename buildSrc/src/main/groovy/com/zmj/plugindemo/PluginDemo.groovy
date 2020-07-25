package com.zmj.plugindemo

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginDemo implements Plugin<Project>{

    @Override
    void apply(Project project) {
        def extension = project.extensions.create("ZMJ1",ExtensionDemo)

        project.afterEvaluate {
            println "Hello ${extension.name}"
        }

        //获取baseExtension 并注册transform
        def transform = new TransformDemo()
        def baseExtensino = project.extensions.getByType(BaseExtension)
        println "bootClass:${baseExtensino.bootClasspath}"
        baseExtensino.registerTransform(transform)
    }
}