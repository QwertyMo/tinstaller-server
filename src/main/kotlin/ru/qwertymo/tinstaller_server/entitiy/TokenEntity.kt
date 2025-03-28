package ru.qwertymo.tinstaller_server.entitiy

import jakarta.persistence.*

@Entity
@Table(name = "USER_TOKEN")
data class TokenEntity(
    @ManyToOne
    @JoinColumn(name = "REPO_ID")
    var user: UserEntity? = null,

    @Id
    @Column(name = "TOKEN")
    var token: String = ""
)
