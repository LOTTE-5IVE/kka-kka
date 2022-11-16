import axios from "axios";

// const headers = {
//   Authorization: `Bearer ${token}`,
// };

export const GetHApi = async (url, token) => {
  if (token) {
    const { data } = await axios
      .get(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });
    return data;
  }
};

export const PostHApi = async (url, body, token) => {
  if (token) {
    const { data } = await axios
      .post(url, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });

    return data;
  }
};

export const PutHApi = async (url, body, token) => {
  if (token) {
    const { data } = await axios
      .put(url, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });
    return data;
  }
};

export const PatchHApi = async (url, body, token) => {
  if (token) {
    const { data } = await axios
      .patch(url, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });

    return data;
  }
};

export const DeleteHApi = async (url, token) => {
  if (token) {
    const { data } = await axios
      .delete(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });
    return data;
  }
};
