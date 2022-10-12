import { useRouter } from "next/router";
import axios from "axios";
import { useEffect } from "react";

export default function oauth() {
  const router = useRouter();

  const getProvider = (state) => {
    if (state.includes("naver")) {
      return "NAVER";
    }
    if (state.includes("kakao")) {
      return "KAKAO";
    }

    if (state.includes("google")) {
      return "GOOGLE";
    }
  };

  const { code, state } = router.query;
  // TODO: code 가지고 /login/token으로 요청해서 우리 서비스의 accessToken 받아오기
  const login = async () => {
    await axios
      .post(`${process.env.NEXT_PUBLIC_LOCAL_URL}/api/login/token`, {
        code: { code }.code,
        providerName: getProvider({ state }.state),
      })
      .then((res) => {
        console.log(res.data);

        const obj = {
          value: res.data.accessToken,
          expire: new Date().getTime() + 1000 * 60 * 15,
        };
        localStorage.setItem("accessToken", JSON.stringify(obj));
      });
  };

  useEffect(() => {
    if (!router.isReady) {
      return;
    }

    login().then(() => {
      console.log("logged in");
    });
  }, [router.isReady]);

  return (
    <>
      code: {code}
      <br />
      state: {state}
    </>
  );
}
