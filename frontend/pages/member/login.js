import Title from "../../components/common/Title";
import EnrollLayout from "../../layouts/EnrollLayout";

export default function login() {
  return (
    <>
      <Title title="로그인" />
      <EnrollLayout
        title="로그인"
        context={
          <>
            SNS로 편리하게 로그인하시고
            <br />
            다양한 서비스를 이용해보세요.
          </>
        }
      />
    </>
  );
}

const encoding = (value) => {
  return encodeURIComponent(value);
};

const SNSLogin = {
  NAVER: {
    CID: process.env.NEXT_PUBLIC_NAVER_CLIENT_ID,
    REDIRECT_URL: encoding(process.env.NEXT_PUBLIC_NAVER_REDIRECT_URL),
    STATE: encoding(process.env.NEXT_PUBLIC_NAVER_STATE),
  },
  KAKAO: {
    CID: process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID,
    REDIRECT_URL: encoding(process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URL),
    STATE: encoding(process.env.NEXT_PUBLIC_KAKAO_STATE),
  },
};

const SNSUri = {
  NAVER: {
    requestUri: `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${SNSLogin.NAVER.CID}&redirect_uri=${SNSLogin.NAVER.REDIRECT_URL}&state=${SNSLogin.NAVER.STATE}`,
  },
  KAKAO: {
    requestUri: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${SNSLogin.KAKAO.CID}&redirect_uri=${SNSLogin.KAKAO.REDIRECT_URL}&state=${SNSLogin.KAKAO.STATE}`,
  },
};
