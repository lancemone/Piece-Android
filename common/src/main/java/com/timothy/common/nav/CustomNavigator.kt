package com.timothy.common.nav

import androidx.navigation.NavDestination
import androidx.navigation.Navigator


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.nav.CustomNavigator
 * @Author: MoTao
 * @Date: 2023-08-30
 * <p>
 * <p/>
 */
abstract class CustomNavigator : Navigator<CustomNavDestination>() {

}


class CustomNavDestination : NavDestination("") {
}