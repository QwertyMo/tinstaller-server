package ru.qwertymo.tinstaller_server.entitiy

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator


@Entity
@Table(name = "APP")
data class AppEntity(
    @Id
    @Column(name = "APP_ID")
    @GeneratedValue
    var id: Int = 0,

    @Column(name = "TITLE")
    var title: String = "",

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