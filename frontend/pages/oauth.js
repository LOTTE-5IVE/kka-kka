import {useRouter} from "next/router";

const oauth = () => {
  const router = useRouter();

  const { code } = router.query;
  // TODO: code 가지고 /login/token으로 요청해서 우리 서비스의 accessToken 받아오기

  return (
      <>

      </>
  )
}

export default oauth;