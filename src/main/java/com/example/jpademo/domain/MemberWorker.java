package com.example.jpademo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member_worker")
public class MemberWorker implements Serializable {
    private static final Long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "member_userName")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
