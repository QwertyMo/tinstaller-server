package ru.qwertymo.tinstaller_server.entitiy

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "REPO")
data class RepoEntity(
    @Id
    @Column(name = "REPO_ID")
    @GeneratedValue
    @JsonIgnore
    var id: Int = 0,

    @Column(name = "NAME")
    var name: String = "",

    @OneToMany(mappedBy = "repo")
    var apps: List<AppEntity> = emptyList()
)