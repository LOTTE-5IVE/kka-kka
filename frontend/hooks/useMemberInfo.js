import axios from "axios";

export const useMemberInfo = async (token) => {
  if (token) {
    const { data } = await axios
      .get("/api/members/me", {
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
