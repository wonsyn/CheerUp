import { writeComment, listComment, editComment } from "@/api/Feature/Comment.js";

const state = {
  commentList: [],
};

const getters = {
  getCommentList() {
    return state.commentList;
  },
};

const mutations = {
  SET_COMMENT_LIST(list) {
    state.commentList = list;
  },
};

const actions = {
  async writeComment(feedId, content, userId) {
    const body = {
      feedId: feedId,
      commentContent: content,
      id: userId,
    };

    await writeComment(
      body,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async listComment(feedId) {
    await listComment(
      feedId,
      ({ data }) => {
        mutations.SET_COMMENT_LIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async editComment(commentId, content) {
    const body = {
      commentContent: content,
      id: commentId,
    };
    await editComment(
      body,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
