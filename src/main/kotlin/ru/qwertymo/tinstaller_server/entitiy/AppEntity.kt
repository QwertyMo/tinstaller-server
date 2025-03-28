package ru.qwertymo.tinstaller_server.entitiy

import jakarta.persistence.*


@Entity
@Table(name = "APP")
data class AppEntity(
    @Id
    @Column(name = "APP_ID")
    @GeneratedValue
    var id: Int = 0,

    @Column(name = "TITLE")
    var title: String = "",

    @Lob
    @Column(name = "DESCRIPTION")
    var description: String = "",

    @Column(name = "URL")
    var url: String = "",

    @Column(name = "CATEGORY")
    var category: String = "",

    @Column(name = "APP_REVIEW")
    var appReview: String? = null,

    @ManyToOne
    @JoinColumn(name = "REPO_ID")
    var repo: RepoEntity? = null

)