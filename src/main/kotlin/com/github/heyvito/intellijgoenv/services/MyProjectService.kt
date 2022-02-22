package com.github.heyvito.intellijgoenv.services

import com.intellij.openapi.project.Project
import com.github.heyvito.intellijgoenv.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
