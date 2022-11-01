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
