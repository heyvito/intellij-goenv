package com.github.heyvito.goenv.services

import com.goide.vgo.configuration.VgoProjectSettings
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class ProjectConfigurationService {
    companion object {
        fun configure(project: Project, using: VirtualFile) {
            val envFile = EnvParser(using.contentsToByteArray())
            if (envFile.privates.isNotEmpty()) {
                ApplicationManager.getApplication().invokeLater {
                    ApplicationManager.getApplication().runWriteAction {
                        val vgo = VgoProjectSettings.getInstance(project)
                        val env = vgo.environment
                        env["GOPRIVATE"] = envFile.privates.joinToString(",")
                        vgo.setEnvironment(env)
                    }
                }
            }
        }
    }
}
