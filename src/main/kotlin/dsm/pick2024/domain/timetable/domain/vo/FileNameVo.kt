package dsm.pick2024.domain.timetable.domain.vo

import java.text.Normalizer

data class FileNameVo(val rawName: String) {
    val value: String = Normalizer.normalize(rawName, Normalizer.Form.NFC)
}
