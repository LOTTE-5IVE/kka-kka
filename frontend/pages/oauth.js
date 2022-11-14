import { useRouter } from "next/router";
import axios from "axios";
import { useEffect, useState } from "react";
import LoginCheckLayout from "../layouts/LoginCheckLayout";

export default function Oauth() {
  const router = useRouter();
  const [loginFlag, setLoginFlag] = useState();

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

  useEffect(() => {
    if (!router.isReady) {
      return;
    }

    const { code, state } = router.query;

    const login = async () => {
      await axios
        .post(`/api/login/token`, {
          code: { code }.code,
          providerName: getProvider({ state }.state),
        })
        .then((res) => {
          setLoginFlag(true);
          const obj = {
            value: res.data.accessToken,
            expire: new Date().getTime() + 1000 * 60 * 60,
          };
          localStorage.setItem("accessToken", JSON.stringify(obj));
        })
        .catch(function (error) {
          console.log(error);
          setLoginFlag(false);
        });
    };

    login();
  }, [router]);

  return (
    <>
      {loginFlag !== undefined ? (
        <LoginCheckLayout loginFlag={loginFlag} />
      ) : (
        ""
      )}
    </>
  );
}
