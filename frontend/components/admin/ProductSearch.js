import { useState } from "react";
import AdminSidebar from "../../components/admin/AdminSidebar";
import ApplyTable from "../../components/admin/ApplyTable";
import ButtonComp from "../../components/common/buttonComp";
import ApplyGrade from "./ApplyGrade";

export default function ProductSearch() {
  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
          </colgroup>
          <thead>
            <th>대표사진</th>
            <th>상품 이름</th>
            <th>상세옵션</th>
            <th>가격</th>
            <th>재고</th>
            <th>카테고리</th>
            <th>수정</th>
            <th>상품 페이지</th>
          </thead>
          <tbody>
            <tr>
              <td>
                <img width="32px" src="/sample.png" />
              </td>
              <td>쿠키 어쩌구 저쩌구 샌드</td>
              <td>600g x 2개 총 1200g</td>
              <td>10,000원</td>
              <td>130</td>
              <td>스낵/쿠키</td>
              <td>
                <input type="button" />
              </td>
              <td>
                <input type="button" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <style jsx>{`
        .contents {
          height: 100%;
          border: 2px solid #dedede;
          color: #7a7a7a;

          table {
            width: 90%;
            height: 90%;
            margin: auto;
            border-collapse: collapse;

            tr {
              border-bottom: 1px solid #dedede;
            }
          }
        }
      `}</style>
    </>
  );
}
