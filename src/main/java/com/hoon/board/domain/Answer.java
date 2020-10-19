package com.hoon.board.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity
public class Answer extends AbstractEntity{

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;
    public User getWriter() {
        return writer;
    }
    @Lob // 255자가 넘는 String 타입일 경우 애노테이션 추가
    @JsonProperty
    private String contents;
    public String getContents() {
        return contents;
    }

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    // 기본 생성자
    public Answer() {
    }

    // 생성자
    public Answer(User writer, Question question2, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.question = question2;
    }

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }

    @Override
    public String toString() {
        return "Answer{" + super.toString() +
                "writer=" + writer +
                ", contents='" + contents + '\'' +
                ", question=" + question +
                '}';
    }
}
