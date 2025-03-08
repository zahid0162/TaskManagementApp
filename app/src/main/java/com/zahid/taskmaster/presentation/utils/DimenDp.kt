package com.tech.clubsystemmemberportal.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun zeroSize() = 0.dp
@Composable
internal fun oneSize() = 1.dp
@Composable
internal fun twoSize() = 2.dp
@Composable
internal fun floatingActionButtonSize() = 70.dp
@Composable
internal fun standardButtonSize() = 50.dp
@Composable
internal fun floatingActionInnerButtonSize() = 50.dp
@Composable
internal fun checkBoxRadius() = 4.dp
@Composable
internal fun cardCornerRadius() = 12.dp
@Composable
internal fun toolTipCornerRadius() = 10.dp
@Composable
internal fun categoryIconCardCornerRadius() = 8.dp
internal fun editTextVerticalPadding()=2.dp
@Composable
internal fun editTextHorizontalPadding()=12.dp
@Composable
internal fun simpleEditTextHeight()=50.dp
internal fun dateEditTextHeight()=40.dp
@Composable
internal fun editTextAnswerHeight()=100.dp
@Composable
internal fun taskEditTextHeight()=30.dp
@Composable
internal fun simpleEditTextRoundedShape()= RoundedCornerShape(2.dp)
@Composable
internal fun shadowShape()= RoundedCornerShape(16.dp)
@Composable
internal fun iconCardSize() = 40.dp
@Composable
internal fun smallIconCardSize() = 35.dp
@Composable
internal fun iconCardShape() = RoundedCornerShape(6.dp)
@Composable
internal fun buttonSize() = 45.dp
@Composable
internal fun buttonCornerRadius() = 7.dp
@Composable
internal fun disableButton() = 0.8f
@Composable
internal fun editTextIconPadding()=8.dp
@Composable
internal fun iconCircularButtonSize()=31.dp
@Composable
internal fun extraSmallIconSize()=16.dp
@Composable
internal fun xxSmallIconSize()=10.dp
@Composable
internal fun smallIconSize()=20.dp
@Composable
internal fun mediumIconSize()=24.dp
@Composable
internal fun iconRoundButtonSize()=31.dp
@Composable
internal fun mediumIconRoundButtonSize()=35.dp
@Composable
internal fun simpleTextStyle()= TextStyle(MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.W700, fontSize = 16.sp, )

@Composable
internal fun alignEndTextStyle()= TextStyle(MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.W700, fontSize = 16.sp,  textAlign = TextAlign.End)
@Composable
internal fun taskTitleTextStyle()= TextStyle(MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.W700, fontSize = 16.sp, )

@Composable
internal fun searchBarTextStyle()= TextStyle(MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.W700, fontSize = 16.sp)

@Composable
internal fun basicEditTextHintStyle(textColor: Color= MaterialTheme.colorScheme.onSecondaryContainer, textSize:TextUnit =16.sp, )= TextStyle(textColor, fontWeight = FontWeight.W700, fontSize = textSize)
@Composable
internal fun alignEndEditTextHintStyle() = TextStyle(MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.W700, fontSize = 16.sp, textAlign = TextAlign.End, textDirection = TextDirection.Rtl)
@Composable fun topRoundedCornerBackgroundShape() = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
@Composable
internal fun dialogCardShape() = RoundedCornerShape(16.dp)

@Composable
internal fun minTaskEditorHeight() = 90.dp

@Composable
internal fun maxTaskEditorHeight() = 135.dp
@Composable
internal fun defaultCategoryDropdownMenuWidth() = 200.dp
@Composable
internal fun defaultCategoryDropdownMenuHeight() = 300.dp

@Composable
internal fun defaultChipWidth() = 135.dp
@Composable
internal fun defaultChipHeight() = 35.dp
@Composable
internal fun shipRadius() = 20.dp
@Composable
internal fun badgeSize() = 10.dp

@Composable
internal fun appbarHeight() = 70.dp

@Composable
internal fun DialogCardShape() = RoundedCornerShape(16.dp)

@Composable
internal fun Modifier.dialogModifier() = composed(factory = {
    this.then(Modifier
        .fillMaxWidth(0.98f)
        .heightIn(min=120.dp)
        .padding(12.dp)
        .background(MaterialTheme.colorScheme.onTertiaryContainer, shape =dialogCardShape() ))
})

object DimenDp{
    val Zero = 0.dp
    val One = 1.dp
    val HUNDRED = 100.dp
    val BorderWidthHalf_dp = 0.5.dp
    val BorderWidthPointTwo_dp = 0.2.dp
    val BorderWidth_2dp = 2.dp
    val XXSmallMargin = 4.dp
    val ExtraSmallMargin = 8.dp
    val ExtraSmallPadding = 8.dp
    val SmallPadding = 12.dp
    val DefaultMargin = 20.dp
    val DefaultPadding = 16.dp
    val MediumPadding = 20.dp
    val LargePadding = 28.dp
    val TopHeadingLargeMargin = 46.dp
    val SmallSpace = 16.dp
    val MediumSpace = 20.dp
    val LargeSpace = 60.dp
    val XLargeSpace = 120.dp
    val TextPadding = 4.dp
    val IconPadding = 2.dp
    val dashedLineHeight = 120.dp
    val defaultSelectableHeight = 100.dp
    val drawerHorizontalPadding = 2.dp
    val defaultCardElevation = 20.dp
    val toolTipElevation = 20.dp
    val chipPadding = 4.dp


}