package dsm.pick2024.global.security.path

object SecurityPaths {
    val PERMIT_ALL_ENDPOINTS = arrayOf(
        "/admin/login",
        "/admin/refresh",
        "/user/login",
        "/user/refresh",
        "/main",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/dsm-pick/swagger-ui/index.html",
        "/dsm-pick/swagger-ui/index.html/**",
        "/user/signup",
        "/mail/send",
        "/mail/check",
        "/admin/signup",
        "/PiCK_Logo.png",
        "/user/password",
        "/admin/key",
        "/admin/password"
    )

    val SCH_GET_ENDPOINTS = arrayOf(
        "/admin/**",
        "/after/**",
        "/application/reason/all",
        "/application/status",
        "/application/floor",
        "/application/grade",
        "/application/all",
        "/story/**",
        "/class-room/floor",
        "/class-room/grade",
        "/early-return/grade",
        "/early-return/floor",
        "/early-return/reason/ok-all",
        "/early-return/ok",
        "/early-return/all",
        "/early-return/ok/{floor}",
        "/self-study/month",
        "/self-study/date",
        "/self-study/admin",
        "/weekend-meal/all",
        "/weekend-meal/hey",
        "/status/grade",
        "/user/all",
        "/timetable/all",
        "/weekend-meal/excel",
        "/weekend-meal/excel/grade",
        "/application/non-return",
        "/attendance/grade",
        "/attendance/total-time/grade",
        "/attendance/club",
        "/attendance/total-time/club",
        "/weekend-meal/application-list",
        "/notice/*",
        "/schedule/date",
        "/schedule/month"
    )
    val SCH_POST_ENDPOINTS = arrayOf(
        "/user/club",
        "/after/**",
        "/meal",
        "/notice",
        "/schedule/create",
        "/self-study/register",
        "/timetable",
        "/weekend-meal/saveAll",
        "/status/saveAll",
        "/schedule/save",
        "/notice/create",
        "/weekend-meal/application-list"
    )
    val SCH_DELETE_ENDPOINTS = arrayOf(
        "/after/**",
        "/notice/delete/**",
        "/schedule/delete/*",
        "/after/delete",
        "/notice/delete",
        "/admin"
    )
    val SCH_PATCH_ENDPOINTS = arrayOf(
        "/application/status",
        "/application/**",
        "/early-return/**",
        "/notice/modify",
        "/status/change",
        "/weekend-meal/status",
        "/schedule/modify",
        "/after/change",
        "/class-room/status",
        "/class",
        "/weekend-meal/period",
        "/timetable/change",
        "/attendance/modify",
        "/attendance/total-time/modify"
    )

    val STU_GET_ENDPOINTS = arrayOf(
        "/user/simple",
        "/user/details",
        "/application/my",
        "/application/simple",
        "/class-room/move",
        "/early-return/my",
        "/timetable/today",
        "/timetable/week",
        "/weekend-meal/my",
        "/notification/**",
        "/weekend-meal/period"
    )
    val STU_POST_ENDPOINTS = arrayOf(
        "/application",
        "/class-room/move",
        "/early-return/create"
    )
    val STU_DELETE_ENDPOINTS = arrayOf(
        "/class-room/return"
    )
    val STU_PATCH_ENDPOINTS = arrayOf(
        "/weekend-meal/my-status",
        "/user/profile",
        "/notification/**"
    )

    val GET_AUTHENTICATED = arrayOf(
        "/meal/date",
        "/self-study/today",
        "/notice/today",
        "/notice/simple"
    )
}
