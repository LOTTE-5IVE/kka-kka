import { useEffect, useState } from "react";
import { PatchHApi } from "../../apis/Apis";
import ButtonComp from "../../components/common/Button/ButtonComp";
import { useEngCheck } from "../../hooks/useEngCheck";
import { useGetToken } from "../../hooks/useGetToken";
import { useLangCheck } from "../../hooks/useLangCheck";
import { useMemberInfo } from "../../hooks/useMemberInfo";
import { useNumberCheck } from "../../hooks/useNumberCheck";
import { useTextCheck } from "../../hooks/useTextCheck";
import Title from "../common/Title";
import DaumPost from "../payment/DaumPost";

export default function MyInfoEdit() {
  const [token, setToken] = useState();
  const [data, setData] = useState();
  const [name, setName] = useState();
  const [email1, setEmail1] = useState();
  const [email2, setEmail2] = useState();
  const [phone1, setPhone1] = useState();
  const [phone2, setPhone2] = useState();
  const [phone3, setPhone3] = useState();
  const [addr1, setAddr1] = useState("");
  const [addr2, setAddr2] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [popup, setPopup] = useState(false);
  const [nameValid, setNameValid] = useState(false);
  const [phoneValid1, setPhoneValid1] = useState(false);
  const [phoneValid2, setPhoneValid2] = useState(false);
  const [phoneValid3, setPhoneValid3] = useState(false);
  const [unvalid, setUnValid] = useState(true);

  function addr1Handler(addr1) {
    setAddr1(addr1);
  }

  function popupHandler() {
    setPopup(!popup);
  }

  function zipcodeHandler(zipcode) {
    setZipcode(zipcode);
  }

  useEffect(() => {
    setToken(useGetToken());

    if (token !== "") {
      useMemberInfo(token).then((res) => {
        if (res) {
          setData(res);
        }
      });
    }
  }, [token]);

  useEffect(() => {
    if (nameValid && phoneValid1 && phoneValid2 && phoneValid3) {
      setUnValid(false);
    } else {
      setUnValid(true);
    }
  }, [nameValid, phoneValid1, phoneValid2, phoneValid3]);

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

    useMemberInfo(token).then((res) => {
      if (res) {
        setData(res);
      }
    });
  };

  return (
    <>
      <Title title="내 정보 수정" />
      {data && (
        <div className="MyInfoEditWrapper">
          <div>
            <div className="tableTitle">기본정보</div>
            <div className="tableContents orderInfo">
              <table>
                <colgroup>
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "80%" }} />
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름</th>
                    <td>{data.name}</td>
                  </tr>

                  <tr>
                    <th scope="row">휴대전화</th>
                    <td>{data.phone}</td>
                  </tr>
                  <tr>
                    <th scope="row">이메일</th>
                    <td>{data.email}</td>
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
            <div className="tableTitle editTitle">
              수정할 정보 <span style={{ color: "red" }}>* 필수항목</span>
            </div>
            <div className="tableContents editTable">
              <table>
                <colgroup>
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "80%" }} />
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">
                      <span style={{ color: "red" }}>*</span>이름
                    </th>
                    <td>
                      <input
                        placeholder=""
                        size="15"
                        defaultValue={name}
                        type="text"
                        onChange={(e) => {
                          if (useLangCheck(e.target.value)) {
                            setName(e.target.value);
                            setNameValid(true);
                          } else {
                            alert("한글 혹은 영문만 입력할 수 있습니다.");
                            setNameValid(false);
                            e.target.value = "";
                          }
                        }}
                      />
                    </td>
                  </tr>

                  <tr>
                    <th scope="row">
                      <span style={{ color: "red" }}>*</span>휴대전화
                    </th>
                    <td>
                      <input
                        maxLength="3"
                        size="3"
                        defaultValue={phone1}
                        type="text"
                        onChange={(e) => {
                          if (useNumberCheck(e.target.value)) {
                            setPhone1(e.target.value);
                            if (e.target.value.length == 3)
                              setPhoneValid1(true);
                            else {
                              setPhoneValid1(false);
                            }
                          } else {
                            alert("숫자만 입력할 수 있습니다.");
                            setPhoneValid1(false);
                            e.target.value = "";
                          }
                        }}
                      />
                      -
                      <input
                        maxLength="4"
                        size="4"
                        defaultValue={phone2}
                        type="text"
                        onChange={(e) => {
                          if (useNumberCheck(e.target.value)) {
                            setPhone2(e.target.value);
                            if (e.target.value.length == 4)
                              setPhoneValid2(true);
                            else {
                              setPhoneValid2(false);
                            }
                          } else {
                            alert("숫자만 입력할 수 있습니다.");
                            setPhoneValid2(false);
                            e.target.value = "";
                          }
                        }}
                      />
                      -
                      <input
                        maxLength="4"
                        size="4"
                        defaultValue={phone3}
                        type="text"
                        onChange={(e) => {
                          if (useNumberCheck(e.target.value)) {
                            setPhone3(e.target.value);
                            if (e.target.value.length == 4)
                              setPhoneValid3(true);
                            else {
                              setPhoneValid3(false);
                            }
                          } else {
                            alert("숫자만 입력할 수 있습니다.");
                            setPhoneValid3(false);
                            e.target.value = "";
                          }
                        }}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">이메일</th>
                    <td>
                      <input
                        defaultValue={email1}
                        type="text"
                        onChange={(e) => {
                          if (useEngCheck(e.target.value)) {
                            setEmail1(e.target.value);
                          } else {
                            alert("영어만 입력할 수 없습니다.");
                            e.target.value = "";
                          }
                        }}
                      />
                      @{" "}
                      <span className="mailAddress">
                        <span className="directInput ec-compact-etc">
                          <input
                            placeholder="직접입력"
                            defaultValue={email2}
                            type="text"
                            onChange={(e) => {
                              if (useTextCheck(e.target.value)) {
                                setEmail2(e.target.value);
                              } else {
                                alert("특수문자는 입력할 수 없습니다.");
                                e.target.value = "";
                              }
                            }}
                          />
                        </span>
                        <select
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
                    <th scope="row" rowSpan="3">
                      주소
                    </th>
                    <td>
                      <div className="address_search">
                        <input
                          placeholder="우편번호"
                          type="text"
                          maxLength="14"
                          readOnly
                          defaultValue={zipcode ? zipcode : ""}
                        />
                        <button
                          onClick={() => {
                            popupHandler();
                          }}
                        >
                          우편번호 찾기
                        </button>
                        {popup && (
                          <DaumPost
                            zipcodeHandler={zipcodeHandler}
                            addrHandler={addr1Handler}
                          />
                        )}
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input
                        placeholder="기본주소"
                        type="text"
                        size="45"
                        maxLength="100"
                        readOnly
                        defaultValue={addr1 ? addr1 : ""}
                        onChange={(e) => {
                          setAddr1(e.target.value);
                        }}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input
                        placeholder="상세주소"
                        type="text"
                        size="45"
                        maxLength="100"
                        defaultValue={addr2 ? addr2 : ""}
                        onChange={(e) => {
                          setAddr2(e.target.value);
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
            <ButtonComp context="수정하기" unvalid={unvalid} />
          </div>
        </div>
      )}
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .MyInfoEditWrapper {
            width: 970px;
            margin: 0 auto;

            .tableTitle {
              font-size: 20px;
              font-weight: 700;
              line-height: 2;
              border-bottom: 2px solid;
            }

            .tableContents {
              margin-bottom: 97px;

              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 951px;

                tr {
                  height: 95px;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .editTitle {
              span {
                font-size: 14px;
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

              button {
                margin: 0 10px;
                line-height: 40px;
                padding: 0 13px;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 16px;
                border: 1px solid #000;
                cursor: pointer;
              }
            }

            .editBtn {
              width: 200px;
              margin: 0 auto 50px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .MyInfoEditWrapper {
            width: 65vw;
            margin: 0 auto;

            .tableTitle {
              font-size: 20px;
              font-weight: 700;
              line-height: 2;
              border-bottom: 2px solid;
            }

            .tableContents {
              margin-bottom: 5vw;

              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 65vw;
                font-size: 2vw;

                tr {
                  height: 7vw;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .editTitle {
              span {
                font-size: 10px;
              }
            }

            .editTable {
              font-size: 2vw;

              input {
                margin: 0 0.53vw;
                line-height: 30px;
                padding: 0 0 0 0.7vw;
                border: 0 none;
                color: #3a3a3a;
                background: #fff;
                border-radius: 8px;
                font-size: 1.5vw;
                border: 1px solid #000;
              }

              select {
                height: 5vw;
                border-radius: 8px;
                font-size: 2vw;
                color: #3a3a3a;
                margin-left: 10px;
                padding-left: 10px;
                border: 1px solid #000;
              }

              button {
                margin: 0 0.53vw;
                line-height: 30px;
                padding: 0 0.7vw;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 2vw;
                border: 1px solid #000;
                cursor: pointer;
              }
            }

            .editBtn {
              width: 20vw;
              margin: 0 auto 5vw;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .MyInfoEditWrapper {
            width: 340px;
            margin: 0 auto;

            .tableTitle {
              font-size: 16px;
              font-weight: 700;
              line-height: 2;
              border-bottom: 2px solid;
            }

            .tableContents {
              margin-bottom: 50px;

              table {
                font-size: 12px;
                border-collapse: collapse;
                margin: 0 auto;
                width: 320px;

                tr {
                  height: 50px;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .editTitle {
              span {
                font-size: 14px;
              }
            }

            .editTable {
              font-size: 15px;

              input {
                max-width: 300px;
                height: 30px;
                margin: 5px 10px;
                line-height: 40px;
                padding: 0 0 0 13px;
                border: 0 none;
                color: #3a3a3a;
                background: #fff;
                border-radius: 8px;
                font-size: 12px;
                border: 1px solid #000;
              }

              select {
                margin: 5px 10px;
                width: 100px;
                height: 25px;
                border-radius: 8px;
                font-size: 12px;
                color: #3a3a3a;
                padding: 0 10px 0 13px;
                border: 1px solid #000;
              }

              button {
                margin: 0 10px;
                width: 100px;
                height: 30px;
                line-height: 30px;
                padding: 0 13px;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 10px;
                font-weight: bold;
                border: 1px solid #000;
                cursor: pointer;
              }
            }

            .editBtn {
              width: 200px;
              margin: 0 auto 50px;
            }
          }
        }
      `}</style>
    </>
  );
}
