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
