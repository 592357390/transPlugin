package com.demon.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class TransPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        println("register trans task" )
        def oriPath

        oriPath = project.rootDir.path + "\\" + project.path.replace(":", "")

//        if (System.getProperty("user.dir").contains(project.path)) {
//            oriPath = System.getProperty("user.dir")
//        } else {
//            oriPath = System.getProperty("user.dir") + "\\" + project.path.replace(":", "")
//        }

        println(oriPath)

        project.getTasks().create("trans", GetStringTask.class, oriPath)
    }
}