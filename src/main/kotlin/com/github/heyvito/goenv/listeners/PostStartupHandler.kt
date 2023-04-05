package com.github.heyvito.goenv.listeners

import com.github.heyvito.goenv.services.ProjectConfigurationService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope

class PostStartupHandler : StartupActivity {
    override fun runActivity(project: Project) {
        ApplicationManager.getApplication().executeOnPooledThread {
            ApplicationManager.getApplication().runReadAction {
                val files =
                    FilenameIndex.getVirtualFilesByName(
                        ".goenv",
                        true,
                        GlobalSearchScope.projectScope(project)
                    )
                thisLogger().info("Opening project $project with files $files")
                if (files.isNotEmpty()) {
                    thisLogger().info("Configuring project")
                    ProjectConfigurationService.configure(project, files.first())
                }
            }
        }
    }
}
