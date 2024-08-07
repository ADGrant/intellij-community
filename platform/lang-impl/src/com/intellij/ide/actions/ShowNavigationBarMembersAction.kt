// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ide.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.KeepPopupOnPerform
import com.intellij.ui.ExperimentalUI

internal class ShowNavigationBarMembersAction : ViewNavigationBarMembersAction() {
  init {
    templatePresentation.keepPopupOnPerform = KeepPopupOnPerform.IfRequested
  }

  override fun update(e: AnActionEvent) {
    super.update(e)
    e.presentation.isEnabledAndVisible = ExperimentalUI.isNewUI()
  }
}