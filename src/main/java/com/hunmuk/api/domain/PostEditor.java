package com.hunmuk.api.domain;

import lombok.Getter;

@Getter
public class PostEditor {

    private String title;
    private String contents;

    public PostEditor(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public static PostEditor.PostEditorBuilder builder() {
        return new PostEditor.PostEditorBuilder();
    }
    public static class PostEditorBuilder {
        private String title;
        private String contents;

        PostEditorBuilder() {
        }

        public PostEditor.PostEditorBuilder title(final String title) {
            if(title != null) {
                this.title = title;
            }
            return this;
        }

        public PostEditor.PostEditorBuilder contents(final String contents) {
            if(contents != null) {
                this.contents = contents;
            }
            return this;
        }

        public PostEditor build() {
            return new PostEditor(this.title, this.contents);
        }

        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", contents=" + this.contents + ")";
        }
    }

}