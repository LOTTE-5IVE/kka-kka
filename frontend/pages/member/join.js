import Title from "../../components/common/Title";
import EnrollLayout from "../../layouts/EnrollLayout";

export default function login() {
  return (
    <div>
      <Title title="회원가입" />
      <EnrollLayout
        title="회원가입"
        context={
          <>
            SNS로 편리하게 회원가입하고
            <br />
            까까 사러 가볼까요?
          </>
        }
      />
    </div>
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
