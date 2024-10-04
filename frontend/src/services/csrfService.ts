import axios from "axios";

export const post = async () => {
  return axios
    .get(`${process.env.REACT_APP_API_BASE_URL}/csrf`, {
      withCredentials: true,
    })
    .then((tokenResp) => {
      let config = {
        headers: {
          "X-CSRF-TOKEN": tokenResp.data.token,
        },
        withCredentials: true,
      };
      return axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/api/v1/post`,
        {},
        config
      );
    })
    .then((res) => res.data);
};
