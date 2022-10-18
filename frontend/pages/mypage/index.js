import { useEffect, useState } from "react";
import { GetHApi } from "../../apis/Apis";
import Title from "../../components/common/Title";
import { useGetToken } from "../../hooks/useGetToken";
import { useMemberInfo } from "../../hooks/useMemberInfo";
import MyPageLayout from "../../layouts/MyPageLayout";

export default function mypage() {
  const [tab, setTab] = useState("order");
  const [token, setToken] = useState("");
  const [name, setName] = useState("");
  const [grade, setGrade] = useState("");
  const [orderList, setOrderList] = useState([]);
  const [offset, setOffset] = useState();

  const getOrders = async () => {
    GetHApi(`/api/members/me/orders?pageSize=3`, token).then((res) => {
      if (res) {
        console.log(res);
        setOrderList(res);
      }
    });
  };

  const getOrders2 = async () => {
    GetHApi(`/api/members/me/orders?orderId=${offset}&pageSize=3`, token).then(
      (res) => {
        if (res) {
          console.log(res);
          setOrderList(res);
        }
      },
    );
  };

  useEffect(() => {
    setToken(useGetToken());

    if (token !== "") {
      useMemberInfo(token).then((res) => {
        if (res) {
          setName(res.name);
          setGrade(res.grade);
          getOrders();
        }
      });
    }
  }, [token]);

  function handleTab(menu) {
    setTab(menu);
  }

  function handleOffset(num) {
    setOffset(num);
  }

  return (
    <>
      <Title title="마이페이지" />
      <MyPageLayout
        name={name}
        grade={grade}
        tab={tab}
        handleTab={handleTab}
        orderList={orderList}
      />
    </>
  );
}
