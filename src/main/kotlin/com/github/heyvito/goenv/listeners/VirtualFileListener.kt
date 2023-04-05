package com.github.heyvito.goenv.listeners

import com.github.heyvito.goenv.services.ProjectConfigurationService
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent
import com.intellij.openapi.vfs.newvfs.events.VFileEvent

internal class VirtualFileListener : BulkFileListener {
    override fun after(events: MutableList<out VFileEvent>) {
        super.after(events)

        for (e in events) {
            thisLogger().info("VFL Event: $e")
            if (e.file == null) {
                continue
            }

            val f = e.file!!
            if (f.name != ".goenv") {
                continue
            }
            for (p in ProjectManager.getInstance().openProjects) {
                if (ProjectRootManager.getInstance(p).fileIndex.isInContent(f)) {
                    ProjectConfigurationService.configure(p, f)
                } else {
                    ProjectConfigurationService.reset(p)
                }
            }
        }
    }
}
