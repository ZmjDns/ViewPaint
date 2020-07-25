package com.zmj.plugindemo

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/25
 * Description :修改编译之后java字节码的plugin
 * 该类依赖于gradle插件，
 * 配置见/buildSrc/build.gradle
 */
class TransformDemo extends Transform {
    @Override
    String getName() {
        return "zmjTransform"   //增加task
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return  TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        //super.transform(transformInvocation)
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider

        inputs.each {
            it.jarInputs.each{
                println "file: ${it.file}"
                File dest = outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes, Format.JAR)
                FileUtils.copyFile(it.file,dest)
            }
            it.directoryInputs.each {
                println "file: ${it.file}"
                File dest = outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.DIRECTORY)
                FileUtils.copyDirectory(it.file,dest)
            }
        }

    }
}
