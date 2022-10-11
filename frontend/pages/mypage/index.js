import axios from "axios";
import { useEffect, useState } from "react";
import Title from "../../components/common/Title";
import MyCoupon from "../../components/mypage/MyCoupon";
import MyInfoCard from "../../components/mypage/MyInfoCard";
import MyOrder from "../../components/mypage/MyOrder";
import Mysidebar from "../../components/mypage/mysidebar";

export default function mypage() {
  const [tab, setTab] = useState("order");
  const [value, setValue] = useState("");
  const [token, setToken] = useState("");
  const [name, setName] = useState("");
  const [grade, setGrade] = useState("");

  function search(event) {
    if (event.key === "Enter") {
      window.location.href = `/product?search=${value}`;
      setValue("");
    }
  }

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const getMemberName = async () => {
    await axios
      .get(`/api/members/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setName(res.data.name);
        setGrade(res.data.grade);
      });
  };

  useEffect(() => {
    const objString = localStorage.getItem("accessToken");
    const obj = JSON.parse(objString);

    if (obj && new Date().getTime() > obj.expire) {
      localStorage.removeItem("accessToken");
      setToken("");
    } else if (obj) {
      setToken(obj.value);
    }

    getMemberName();
  }, [token]);

  function handleTab(menu) {
    setTab(menu);
  }

  return (
    <>
      <Title title="마이페이지" />
      <div>
        <div className="contents">
          <div className="sidebar">
            <Mysidebar mypageCallback={handleTab} />
          </div>
          <div className="wrapper">
            <div>
              <MyInfoCard name={name} grade={grade} />
            </div>

            <div className="mypageMenu">
              {tab == "order" ? <MyOrder /> : <MyCoupon />}
            </div>
          </div>
        </div>
        <style jsx>{`
          .contents {
            margin: 0 auto;
            width: 70%;
            display: flex;
            .sidebar {
              display: inline-block;
              width: 18%;
              margin-right: 7%;
            }

            .wrapper {
              max-width: 970px;
              width: 75%;

              .myorder {
                .myorderTitle {
                  font-size: 24px;
                  font-weight: 700;
                  color: #3a3a3a;
                  border-bottom: 2px solid #3a3a3a;
                  line-height: 24px;
                  padding-bottom: 15px;
                }

                table {
                  width: 100%;
                  border-collapse: collapse;
                  font-size: 14px;
                  font-weight: 600;
                  color: #2c2c2c;

                  tr:nth-child(4n + 2) td:nth-child(3) {
                    text-align: right;
                    text-decoration: line-through;
                    padding-right: 20px;
                  }

                  tr:not(:nth-child(4n + 2)) td:nth-child(2) {
                    text-align: right;
                    padding-right: 20px;
                  }
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
