import {useContext, useEffect, useState} from "react";
import {AdminProvider} from "../../context/AdminTokenContext";
import axios from "axios";
import {useRouter} from "next/router";
import {ThemeGray} from "../../typings/ThemeColor";
import {NGray, NLightGray} from "../../typings/NormalColor";

export default function Login() {
  const [userInfo, setUserInfo] = useState({
    'userId': '',
    'password': ''
  });

  const userData = useContext(AdminProvider);
  const isLoggedIn = useContext(AdminProvider)?.isLoggedIn;

  const router = useRouter;

  const handleChange = (e) => {
    setUserInfo((prevState) => {
      return {
        ...prevState,
        [e.target.name]: e.target.value
      }
    });
  }

  const handleSubmit = async () => {
    await axios.post('http://localhost:8080/login/token', {
      'userId': userInfo.userId,
      'password': userInfo.password,
    })
    .then((res) => {
      if (res.status === 200) {
        console.log(res.data)
        localStorage.setItem('adminToken', res.data.adminToken);
        userData.getUser();
      }
    })
    .catch(() => {
      alert('로그인에 실패했습니다.');
    })
  }

  useEffect(() => {
    if (isLoggedIn) {
      router().push('/admin')
    }
  }, [isLoggedIn])

  return (
      <>
        <div className='wrapper'>
          <div className='contents'>
            <div className="logo">
              <img width="155" src="/main/logo.png"/>
            </div>
            <form
                className={'login-form'}
            >
              <p className='h4'>관리자 로그인</p>
              <div>
                <div className='input-item'>
                  <label>아이디</label>
                  <input
                      onChange={handleChange}
                      name={'userId'}
                      required={true}
                      autoComplete={'username'}
                  />
                </div>
                <div className='input-item'>
                  <label>비밀번호</label>
                  <input
                      type={"password"}
                      onChange={handleChange}
                      name={'password'}
                      required={true}
                      autoComplete={'current-password'}
                  />
                </div>
              </div>
              <button onClick={handleSubmit}
                      className="login-btn">
                로그인
              </button>
            </form>
          </div>
        </div>
        <style jsx>{`
          .wrapper {
            height: 100vh;
            background: ${ThemeGray};
            
            color: #1a1a1a;
          }
          
          .contents {
            display: flex;
            flex-direction: column;
            justify-contents: center;
            align-items: center;
          }
          
          .logo {
            margin-top: 5rem;
            padding: 2rem;
          }
          
          .login-form {
            display: flex;
            flex-direction: column;
            justify-contents: center;
            align-items: center;
            
            padding: 4rem;
            background: white;
            border-radius: 30px;
            
            input {
              margin: 1rem;
              border: 0;
              padding: 0.5rem;
              background: #ececec;
              border-radius: 5px;
            }
            
            .input-item {
              display: flex;
              align-items: center;
              justify-content: space-between;
              margin-bottom: 0.8rem;
            }
          }
          
          .h4 {
            font-size: 1.5rem;
          }
          
          .login-btn {
            margin: 1rem 0 0.3rem 0;
          }
        `}
        </style>
      </>
  );
}

Login.displayName = 'AdminLogin';
