package emy.backend.lawapp50.route.status

import emy.backend.lawapp50.route.GlobalRoute

object StatusScope {
    const val PUBLIC = "${GlobalRoute.PUBLIC}/status"
    const val PROTECTED = "${GlobalRoute.PROTECT}/status"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/status"
}
