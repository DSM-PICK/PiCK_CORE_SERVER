package dsm.pick2024.domain.bug

import org.apache.logging.log4j.util.StringMap

data class BugRequest (
    val title: String,
    val content: String,
    val fileName: String?
)
