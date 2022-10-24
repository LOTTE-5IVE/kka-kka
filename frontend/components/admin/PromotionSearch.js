import axios from "axios";
import { useEffect, useState } from "react";
import { AdminButton } from "../common/Button/AdminButton";
import CouponSearchTable from "./CouponSearchTable";
import DiscountSearchTable from "./DiscountSearchTable";

export default function ProductSearch() {
  const [tab, setTab] = useState("할인");

  // const getPromotion = async () => {
  //   await axios.get("/api/coupons/discount").then((res) => {
  //     console.log(res.data);
  //   });
  // };

  // useEffect(() => {
  //   getPromotion();
  // }, []);

  return (
    <div className="wrapper">
      <div style={{ display: "flex", marginBottom: "30px" }}>
        <div
          className={`tab ${tab === "할인" ? "active" : ""}`}
          onClick={() => setTab("할인")}
        >
          할인
        </div>
        <div
          className={`tab ${tab === "쿠폰" ? "active" : ""}`}
          onClick={() => setTab("쿠폰")}
        >
          쿠폰
        </div>
      </div>
      <div className="contents">
        {tab === "할인" ? <DiscountSearchTable /> : <CouponSearchTable />}
      </div>

      <style jsx>{`
        .wrapper {
          width: 90%;
          height: 100%;
          margin: 0 auto;
          font-size: 0.8rem;

          .tab {
            border: 1px solid;
            padding: 7px 25px;
            border-radius: 1em;
          }

          .active {
            background-color: #fe5c57;
            color: #fff;
            border: 1px solid #fe5c57;
            border-radius: 1em;
          }

          .contents {
            height: 100%;
            border: 2px solid #dedede;
            color: #7a7a7a;
            background: #fcfcfc;
            border-radius: 2em;
          }
        }
      `}</style>
    </div>
  );
}
