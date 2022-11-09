import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { fetchSearchData } from "../../apis/SearchApi";

export default function SearchFilter({setResource, search, page}) {
  const [category, setCategory] = useState([]);
  const [sort, setSort] = useState("");
  const [toggle, setToggle] = useState(false);
  const [minPrice, setMinPrice] = useState(0);
  const [maxPrice, setMaxPrice] = useState(0);
  const [minCalorie, setMinCalorie] = useState(0);
  const [maxCalorie, setMaxCalorie] = useState(0);

  const handleSingleCheck = (checked, product) => {
    if (checked) {
      setCategory((prev) => [...prev, product]);
    } else {
      setCategory(category.filter((el) => el !== product));
    }
  };

  const searchFilter = async () => {
    setResource (fetchSearchData(sort, page, search, category, minPrice, maxPrice, minCalorie, maxCalorie));
  }

  useEffect(() => {
    console.log(category);
  }, [category]);

  useEffect(() => {
    searchFilter();
  },[page]);

  return (
    <div className="SFWrapper">
      <div className="menu">
        <div
          className={!toggle ? "filter" : "filter toggle"}
          style={{ cursor: "pointer" }}
        >
          {!toggle ? (
            <div
              className="filterBtn"
              onClick={() => {
                setToggle(true);
              }}
            >
              필터 열기 <img src="/product/searchfilter_off.png" />
            </div>
          ) : (
            <div
              className="filterBtn"
              onClick={() => {
                setToggle(false);
              }}
            >
              필터 닫기 <img src="/product/searchfilter_on.png" />
            </div>
          )}
        </div>
        <select
          onChange={(e) => {
            setSort(e.target.value);
          }}
          defaultValue={""}
        >
          <option value="accuracy">정확도순</option>
          <option value="asc">최저가순</option>
          <option value="desc">최고가순</option>
        </select>{" "}
      </div>
      {toggle && (
        <div className="searchFilter">
          <table>
            <colgroup>
              <col style={{ width: "15%" }} />
              <col style={{ width: "85%" }} />
            </colgroup>
            <tbody>
              <tr>
                <th>카테고리</th>
                <td>
                  <ul>
                    <li>
                      <input
                        type="checkbox"
                        id="비스킷/샌드"
                        onChange={(e) => handleSingleCheck(e.target.checked, 1)}
                      />
                      <label for="비스킷/샌드"> 비스킷/샌드</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="스낵/봉지과자"
                        onChange={(e) => handleSingleCheck(e.target.checked, 2)}
                      />
                      <label for="스낵/봉지과자"> 스낵/봉지과자</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="박스과자"
                        onChange={(e) => handleSingleCheck(e.target.checked, 3)}
                      />

                      <label for="박스과자"> 박스과자</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="캔디/사탕/젤리"
                        onChange={(e) => handleSingleCheck(e.target.checked, 4)}
                      />

                      <label for="캔디/사탕/젤리"> 캔디/사탕/젤리</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="시리얼/바"
                        onChange={(e) => handleSingleCheck(e.target.checked, 5)}
                      />

                      <label for="시리얼/바"> 시리얼/바</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="초콜릿"
                        onChange={(e) => handleSingleCheck(e.target.checked, 6)}
                      />

                      <label for="초콜릿"> 초콜릿</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="껌/자일리톨"
                        onChange={(e) => handleSingleCheck(e.target.checked, 7)}
                      />

                      <label for="껌/자일리톨"> 껌/자일리톨</label>
                    </li>
                    <li>
                      <input
                        type="checkbox"
                        id="선물세트"
                        onChange={(e) => handleSingleCheck(e.target.checked, 8)}
                      />

                      <label for="선물세트"> 선물세트</label>
                    </li>
                  </ul>
                </td>
              </tr>
              <tr>
                <th>가격</th>
                <td>
                <input type="text" size="3"  onChange={(e) => {setMinPrice(e.target.value);
                  console.log(minPrice);}} />원 ~{" "}
                  <input type="text" size="3" onChange={(e) => {setMaxPrice(e.target.value);
                  console.log(maxPrice);}}/>원
                </td>
              </tr>
              <tr>
                <th>칼로리</th>
                <td>
                <input type="text" size="3" onChange={(e) => {setMinCalorie(e.target.value);
                  console.log(minCalorie);}}/>
                  kcal ~ <input type="text" size="3" onChange={(e) => {setMaxCalorie(e.target.value);
                  console.log(maxCalorie);}}/>kcal
                </td>
              </tr>
              <tr>
                <td colSpan="2" style={{ border: "0", textAlign: "right" }}>
                  <button
                    onClick={() => {
                      console.log("click");
                      searchFilter();
                    }}
                  >
                    검색
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      )}

      <style jsx>{`
        .SFWrapper {
          width: 100%;

          .menu {
            display: flex;
            justify-content: space-between;

            .filter {
              display: flex;
              align-items: center;
              border: 1px solid #e5e5e5;
              color: #3a3a3a;

              .filterBtn {
                display: flex;
                align-items: center;
              }
            }

            .toggle {
              color: #ff3d45;
              border: 1px solid #ff3d45;
            }
            select {
              border: 1px solid #e5e5e5;
              overflow: hidden;
              background: #fff;
            }
          }

          .searchFilter {
            position: relative;
            border-top: 1px solid #1a1a1a;

            table {
              width: 100%;
              border: 0;
              border-spacing: 0;
              border-collapse: collapse;

              th {
                border-bottom: 1px solid #e5e5e5;
              }

              td {
                border-bottom: 1px solid #e5e5e5;

                button {
                  margin: 0;
                  border: 1px solid #ff3d44;
                  background-color: #ff3d44;
                  color: #fff;
                  transition: 0.7s;
                }

                button:hover {
                  background-color: #fff;
                  color: #ff3d44;
                }
              }

              ul {
                list-style: none;
                display: flex;
                flex-wrap: wrap;
                justify-content: flex-start;
                align-items: center;
                padding: 0;

                li {
                  display: flex;
                  align-items: center;
                  color: #707070;

                  label {
                    cursor: pointer;
                  }
                }
              }
            }
          }
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .SFWrapper {
            .menu {
              .filter {
                border-radius: 10px;
                padding: 0 14px;
                height: 38px;
                font-size: 16px;

                .filterBtn {
                  img {
                    margin-left: 5px;
                  }
                }
              }

              select {
                border-radius: 10px;
                width: 100px;
                padding: 0 0 0 10px;
                height: 40px;
              }
            }

            .searchFilter {
              margin: 30px 0 50px 0;

              table {
                th {
                  padding: 20px 0px;
                }

                td {
                  padding: 20px 0px;

                  button {
                    width: 75px;
                    height: 30px;
                    line-height: 30px;
                    border-radius: 10px;
                  }
                }

                ul {
                  li {
                    width: 25%;
                    padding: 0 10px;
                    font-size: 16px;

                    label {
                      margin-left: 5px;
                    }
                  }
                }
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .SFWrapper {
            .menu {
              .filter {
                border-radius: 10px;
                padding: 0 14px;
                height: 30px;
                font-size: 10px;

                .filterBtn {
                  img {
                    margin-left: 5px;
                  }
                }
              }

              select {
                font-size: 10px;
                border-radius: 10px;
                width: 80px;
                padding: 0 0 0 10px;
                height: 30px;
              }
            }

            .searchFilter {
              margin: 20px 0 30px 0;

              table {
                th {
                  font-size: 10px;
                  padding: 10px 0px;
                }

                td {
                  padding: 10px 0px;

                  button {
                    width: 75px;
                    height: 30px;
                    line-height: 30px;
                    border-radius: 10px;
                  }
                }

                ul {
                  li {
                    width: 50%;
                    padding: 0 5px;
                    font-size: 12px;

                    label {
                      margin-left: 3px;
                    }
                  }
                }
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .SFWrapper {
            margin-top: 15px;

            .menu {
              .filter {
                border-radius: 10px;
                padding: 0 14px;
                height: 40px;
                font-size: 16px;

                .filterBtn {
                  img {
                    margin-left: 5px;
                  }
                }
              }

              select {
                border-radius: 10px;
                width: 100px;
                padding: 0 0 0 10px;
                height: 40px;
              }
            }

            .searchFilter {
              margin: 20px 0;

              table {
                th {
                  padding: 20px 0px;
                }

                td {
                  padding: 20px 0px;

                  button {
                    width: 75px;
                    height: 30px;
                    line-height: 30px;
                    border-radius: 10px;
                  }
                }

                ul {
                  li {
                    width: 50%;
                    padding: 0 10px;
                    font-size: 16px;

                    label {
                      margin-left: 5px;
                    }
                  }
                }
              }
            }
          }
        }
      `}</style>
    </div>
  );
}
