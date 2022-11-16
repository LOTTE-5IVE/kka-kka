import {useContext, useState, useEffect} from "react";
import AdminSidebar from "../../components/admin/AdminSidebar";
import ProductSearch from "../../components/admin/ProductSearch";
import PromotionEnroll from "../../components/admin/PromotionEnroll";
import PromotionSearch from "../../components/admin/PromotionSearch";

import Title from "../../components/common/Title";
import {UserContext} from "../../context/AdminTokenContext";
import {useRouter} from "next/router";

export default function Admin() {
  const [Lmenu, setLmenu] = useState("프로모션");
  const [Smenu, setSmenu] = useState("혜택 등록");

  const isLoggedIn = useContext(UserContext)?.isLoggedIn;
  const router = useRouter();

  function LmenuHandler(text) {
    setLmenu(text);
  }

  function SmenuHandler(text) {
    setSmenu(text);
  }

  useEffect(() => {
    if (!isLoggedIn) {
      router.push('/admin/login')
    }
  }, [isLoggedIn])

  return (
    <>
      <Title title="관리자" />
      <div className="wrapper">
        <div className="sidebar">
          <AdminSidebar
            LmenuHandler={LmenuHandler}
            SmenuHandler={SmenuHandler}
          />
        </div>
        <div className="promotions">
          <div className="title">
            <span>
              {Lmenu} &gt; {Smenu}
            </span>
          </div>
          <div className="contents">
            {Smenu == "혜택 등록" && <PromotionEnroll />}
            {Smenu == "혜택 조회/수정" && <PromotionSearch />}
            {Smenu == "상품 조회/수정" && <ProductSearch />}
          </div>
        </div>
      </div>
      <style jsx>{`
        .wrapper {
          display: flex;
          height: 100vh;

          .sidebar {
            display: inline-block;
            width: 15%;
            height: 110%;
            border-right: 1px solid;
          }

          .promotions {
            width: 100%;
            .title {
              height: 8%;
              width: 100%;
              padding: 15px 20px;
              border-bottom: 1px solid;
            }

            .contents {
              height: 110%;
              padding: 40px 30px;
              background: #ebebeb;
            }
          }
        }
      `}</style>
    </>
  );
}

Admin.displayName = "Admin";
