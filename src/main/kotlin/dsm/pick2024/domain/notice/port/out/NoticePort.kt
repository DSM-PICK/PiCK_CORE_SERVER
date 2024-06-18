package dsm.pick2024.domain.notice.port.out

interface NoticePort :
    NoticeSavePort,
    QueryNoticePort,
    DeleteNoticePort
