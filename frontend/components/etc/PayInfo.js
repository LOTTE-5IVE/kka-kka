import { useEffect, useState } from "react";

export default function PayInfo({ zip, addr }) {
  return (
    <>
      <tr>
        <th scope="row">주문자</th>
        <td>
          <input
            id="oname"
            className="inputTypeText"
            placeholder=""
            size="15"
            value=""
            type="text"
          />
        </td>
      </tr>
      <tr>
        {/* https://choiiis.github.io/web/toy-project-sign-up-and-in-page-2/ */}
        <th scope="row">이메일</th>
        <td>
          <input id="oemail1" className="mailId" value="" type="text" />@{" "}
          <span className="mailAddress">
            <span className="directInput ec-compact-etc">
              <input id="oemail2" placeholder="직접입력" value="" type="text" />
            </span>
            <select id="oemail3">
              <option value="" selected="selected">
                -이메일 선택-
              </option>
              <option value="naver.com">naver.com</option>
              <option value="daum.net">daum.net</option>
              <option value="nate.com">nate.com</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="yahoo.com">yahoo.com</option>
              <option value="empas.com">empas.com</option>
              <option value="korea.com">korea.com</option>
              <option value="dreamwiz.com">dreamwiz.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="etc">직접입력</option>
            </select>
          </span>
        </td>
      </tr>
      <tr>
        <th scope="row">휴대전화</th>
        <td>
          <select id="ophone1_1">
            <option value="02">02</option>
            <option value="031">031</option>
            <option value="032">032</option>
            <option value="033">033</option>
            <option value="041">041</option>
            <option value="042">042</option>
            <option value="043">043</option>
            <option value="044">044</option>
            <option value="051">051</option>
            <option value="052">052</option>
            <option value="053">053</option>
            <option value="054">054</option>
            <option value="055">055</option>
            <option value="061">061</option>
            <option value="062">062</option>
            <option value="063">063</option>
            <option value="064">064</option>
            <option value="070">070</option>
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
          </select>
          -
          <input id="ophone1_2" maxlength="4" size="4" value="" type="text" />
          -
          <input id="ophone1_3" maxlength="4" size="4" value="" type="text" />
        </td>
      </tr>
      <tr>
        <th scope="row" rowSpan="3">
          주소
        </th>
        <td></td>
      </tr>
      <tr>
        <td>우편번호 test{zip}</td>
      </tr>
      <tr>
        <td>기본주소 test{addr}</td>
      </tr>

      <style jsx>{`
        .wrapper {
          width: 70%;
          margin: 0 auto;

          .title {
            text-align: center;

            h2 {
              padding: 0;
              color: #3a3a3a;
              font-size: 36px;
              font-weight: 700;
              line-height: 1;
            }
          }

          .orderList {
            margin-bottom: 10%;
            border-bottom: 2px solid;
            table {
              border-collapse: collapse;
              margin: 0 auto;
              width: 98%;

              tr:not(:last-child) {
                height: 2vw;
                border-bottom: 1.5px solid #d0cfcf;
              }
            }
          }

          .tableTitle {
            font-size: 20px;
            font-weight: 500;
            line-height: 2;
            border-bottom: 2px solid;
          }

          .tableContents {
            margin-bottom: 10%;

            table {
              border-collapse: collapse;
              margin: 0 auto;
              width: 98%;

              tr {
                height: 5vw;
                border-bottom: 1.5px solid #d0cfcf;
              }
            }
          }

          .cp {
            td {
              text-align: right;
            }

            tr:last-child {
              background-color: #f8f8f8;
            }
          }

          .payInfo {
            td {
              text-align: right;
            }

            tr:last-child {
              background-color: #f8f8f8;
            }
          }

          .payBtn {
            width: 20%;
            margin: 0 auto 50px;
          }
        }
      `}</style>
    </>
  );
}
