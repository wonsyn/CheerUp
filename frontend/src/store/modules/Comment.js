import { writeComment, listComment, editComment, deleteComment, addLike, deleteLike, checkLike } from "@/api/Feature/Comment.js";

const state = {
  commentList: [],
  likeState: Boolean,
};

const getters = {
  getCommentList() {
    return state.commentList;
  },
  getLikeState() {
    return state.likeState;
  },
};

const mutations = {
  SET_COMMENT_LIST(list) {
    state.commentList = list;
  },
  SET_LIKE_STATE(data) {
    state.likeState = data;
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
      commentId: commentId,
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

  async deleteComment(commentId) {
    await deleteComment(
      commentId,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async addLike(commentId, userId) {
    const body = {
      commentId: commentId,
      userId: userId,
    };
    await addLike(
      body,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async deleteLike(commentId, userId) {
    const body = {
      commentId: commentId,
      userId: userId,
    };
    await deleteLike(
      body,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async checkLike(commentId, userId) {
    const params = {
      commentId: commentId,
      userId: userId,
    };
    await checkLike(
      params,
      ({ data }) => {
        if (data.message == false) {
          mutations.SET_LIKE_STATE(false);
        } else {
          mutations.SET_LIKE_STATE(true);
        }
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
