package ru.qwertymo.tinstaller_server.entitiy

import jakarta.persistence.*

@Entity
@Table(name = "USERS")
data class UserEntity(
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    var id: Int = 0,

    @Column(name = "NAME")
    var name: String = "",

    @Column(name = "PASSWORD")
    var password: String = "",

    @OneToMany(mappedBy = "token")
    var apps: List<TokenEntity> = emptyList()
)