import { useEffect, useState } from "react";
import { PatchHApi } from "../../apis/Apis";
import ButtonComp from "../../components/common/Button/ButtonComp";
import { useGetToken } from "../../hooks/useGetToken";
import { useMemberInfo } from "../../hooks/useMemberInfo";
import Title from "../common/Title";

export default function MyInfoEdit() {
  const [token, setToken] = useState();
  const [data, setData] = useState();
  const [name, setName] = useState();
  const [email1, setEmail1] = useState();
  const [email2, setEmail2] = useState();
  const [phone1, setPhone1] = useState();
  const [phone2, setPhone2] = useState();
  const [phone3, setPhone3] = useState();

  useEffect(() => {
    setToken(useGetToken());

    useMemberInfo(useGetToken()).then((res) => {
      if (res) {
        setData(res);
      }
    });
  }, []);

  const editMemberInfo = async () => {
    PatchHApi("/api/members/me", { name: name }, token);
    PatchHApi(
      "/api/members/me",
      {
        email: email1 + "@" + email2,
        phone: phone1 + "-" + phone2 + "-" + phone3,
      },
      token,
    );

    useMemberInfo(useGetToken()).then((res) => {
      if (res) {
        setData(res);
      }
    });
  };

  return (
    <>
      <Title title="내 정보 수정" />
      {data && (
        <div className="wrapper">
          <div>
            <div className="tableTitle">기본정보</div>
            <div className="tableContents orderInfo">
              <table>
                <colgroup>
                  <col style={{ width: "15%" }} />
                  <col style={{ width: "85%" }} />
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름</th>
                    <td>{data.name}</td>
                  </tr>
                  <tr>
                    <th scope="row">이메일</th>
                    <td>{data.email}</td>
                  </tr>
                  <tr>
                    <th scope="row">휴대전화</th>
                    <td>{data.phone}</td>
                  </tr>
                  <tr>
                    <th scope="row">기본주소</th>
                    <td>{data.address}</td>
                  </tr>
                  <tr>
                    <th scope="row">등급</th>
                    <td>{data.grade}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div>
            <div className="tableTitle editTable">수정할 정보</div>
            <div className="tableContents editTable">
              <table>
                <colgroup>
                  <col style={{ width: "15%" }} />
                  <col style={{ width: "85%" }} />
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름</th>
                    <td>
                      <input
                        id="oname"
                        className="inputTypeText"
                        placeholder=""
                        size="15"
                        defaultValue={name}
                        type="text"
                        onChange={(e) => {
                          setName(e.target.value);
                        }}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">이메일</th>
                    <td>
                      <input
                        id="oemail1"
                        className="mailId"
                        defaultValue={email1}
                        type="text"
                        onChange={(e) => {
                          setEmail1(e.target.value);
                        }}
                      />
                      @{" "}
                      <span className="mailAddress">
                        <span className="directInput ec-compact-etc">
                          <input
                            id="oemail2"
                            placeholder="직접입력"
                            defaultValue={email2}
                            type="text"
                            onChange={(e) => {
                              setEmail2(e.target.value);
                            }}
                          />
                        </span>
                        <select
                          id="oemail3"
                          onChange={(e) => {
                            setEmail2(e.target.value);
                          }}
                          defaultValue={"DEFAULT"}
                        >
                          <option value="DEFAULT" disabled>
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
                      <input
                        maxLength="3"
                        size="3"
                        defaultValue={phone1}
                        type="text"
                        onChange={(e) => {
                          setPhone1(e.target.value);
                        }}
                      />
                      -
                      <input
                        maxLength="4"
                        size="4"
                        defaultValue={phone2}
                        type="text"
                        onChange={(e) => {
                          setPhone2(e.target.value);
                        }}
                      />
                      -
                      <input
                        maxLength="4"
                        size="4"
                        defaultValue={phone3}
                        type="text"
                        onChange={(e) => {
                          setPhone3(e.target.value);
                        }}
                      />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div
            className="editBtn"
            onClick={() => {
              editMemberInfo();
            }}
            style={{ cursor: "pointer" }}
          >
            <ButtonComp context="수정하기" />
          </div>
        </div>
      )}
      <style jsx>{`
        .wrapper {
          width: 100%;
          margin: 0 auto;

          .tableTitle {
            font-size: 20px;
            font-weight: 700;
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

          .editTable {
            font-size: 20px;

            input {
              margin: 0 10px;
              line-height: 40px;
              padding: 0 0 0 13px;

              border: 0 none;
              color: #3a3a3a;
              background: #fff;
              border-radius: 8px;
              font-size: 16px;
              border: 1px solid #000;
            }

            select {
              height: 40px;
              border-radius: 8px;
              font-size: 16px;
              color: #3a3a3a;
              padding: 0 0px 0 13px;
              border: 1px solid #000;
            }
          }

          .editBtn {
            width: 20%;
            margin: 0 auto 50px;
          }
        }
      `}</style>
    </>
  );
}
