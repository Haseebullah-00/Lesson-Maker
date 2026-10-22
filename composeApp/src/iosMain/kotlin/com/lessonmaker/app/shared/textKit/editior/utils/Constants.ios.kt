package com.lessonmaker.app.shared.textkit.editor.utils

import com.lessonmaker.app.shared.textkit.editor.utils.SPACE

actual val TABS: String
    get() = "\t"

actual val TASK_DECORATOR_INTERACTIVE: String
    get() = "☐$SPACE"

actual val TASK_DECORATOR_UNCHECKED_INTERACTIVE: String
    get() = "☑$SPACE"