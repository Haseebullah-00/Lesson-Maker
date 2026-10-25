//package com.lessonmaker.app.shared.textkit.theme.tokens
//
//import androidx.compose.ui.graphics.Color
//
//internal object TextKitLightTokens  {
//
//    val PRIMARY = Color(0xFF046B5C)
//    val ON_PRIMARY = Color(0xFFFFFFFF)
//    val PRIMARY_CONTAINER = Color(0xFFA7F2DE)
//    val ON_PRIMARY_CONTAINER = Color(0xFF00201A)
//    val SECONDARY = Color(0xFF4A635D)
//    val ON_SECONDARY = Color(0xFFFFFFFF)
//    val HIGHLIGHT = Color(0xFFFFE9A8)
//    val ON_HIGHLIGHT = Color(0xFF3D2E00)
//    val BACKGROUND = Color(0xFFF5FBF7)
//    val ON_BACKGROUND = Color(0xFF171D1B)
//    val SURFACE = Color(0xFFF5FBF7)
//    val ON_SURFACE = Color(0xFF171D1B)
//    val SURFACE_VARIANT = Color(0xFFDBE5E0)
//    val ON_SURFACE_VARIANT = Color(0xFF3F4945)
//    val OUTLINE = Color(0xFF6F7975)
//    val OUTLINE_VARIANT = Color(0xFFBFC9C3)
//    val ERROR = Color(0xFFBA1A1A)
//    val ON_ERROR = Color(0xFFFFFFFF)
//
//}


package com.lessonmaker.app.shared.textkit.theme.tokens

import androidx.compose.ui.graphics.Color
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.deleteBtnText
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldHint
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr

internal object TextKitLightTokens {
    val PRIMARY = fieldCursor
    val ON_PRIMARY = White
    val PRIMARY_CONTAINER = dataCellInfoBoxBg
    val ON_PRIMARY_CONTAINER = fieldCursor
    val SECONDARY = loginTabUnselectedTxtClr
    val ON_SECONDARY = White
    val HIGHLIGHT = Color(0xFFFFE9A8)
    val ON_HIGHLIGHT = Color(0xFF3D2E00)
    val BACKGROUND = White
    val ON_BACKGROUND = fieldText
    val SURFACE = White
    val ON_SURFACE = fieldText
    val SURFACE_VARIANT = Color(0xFFF8FAFC)
    val ON_SURFACE_VARIANT = loginTabUnselectedTxtClr
    val OUTLINE = fieldHint
    val OUTLINE_VARIANT = fieldBorder
    val ERROR = deleteBtnText
    val ON_ERROR = White
}