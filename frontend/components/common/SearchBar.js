import { router } from "next/router";
import { useEffect } from "react";
import { useRef } from "react";
import { useState } from "react";
import { fetchAutoData } from "../../apis/AutoComplete";

export default function SearchBar() {
  const [keyword, setKeyword] = useState();
  const [resource, setResource] = useState();
  const [index, setIndex] = useState(-1);
  const [over, setOver] = useState(false);
  const autoRef = useRef(null);
  const inputRef = useRef(null);

  const data = resource?.ProductNames.read();

  const autoComplete = async () => {
    if (keyword) setResource(fetchAutoData(keyword));
  };

  const onChangeData = (e) => {
    setKeyword(e.currentTarget.value);
  };

  const searchQuery = () => {
    if (keyword.length < 2 || keyword.length > 20) {
      alert("두 글자 이상 스무 글자 이하로 입력하세요.");
      return;
    }

    router.push(
      {
        pathname: `/product`,
        query: { cat_id: 0, search: keyword },
      },
      `/product`,
    );
    setIndex(-1);
  };

  function searchClick(name) {
    setOver(false);
    inputRef.current.value = name;
    setKeyword(name);

    router.push(
      {
        pathname: `/product`,
        query: { cat_id: -1, search: name },
      },
      `/product`,
    );

    setIndex(-1);
  }

  const handleKeyArrow = (e) => {
    if (!data) return;

    if (data.autoKeyword.length > 0) {
      switch (e.key) {
        case "ArrowDown":
          setIndex(index + 1);
          if (autoRef.current?.childElementCount === index + 1) setIndex(0);
          break;
        case "ArrowUp":
          setIndex(index - 1);
          if (index <= 0) {
            setIndex(-1);
          }
          break;
        case "Escape":
          setIndex(-1);
          break;
        case "Enter":
          setOver(false);

          if (index === -1) {
            searchQuery();
            break;
          }
          let inputKeyword =
            autoRef.current.childNodes[index].childNodes[0].innerText;
          setKeyword(inputKeyword);
          inputRef.current.value = inputKeyword;
          searchClick(inputKeyword);
          break;
        default:
          setIndex(-1);
      }
    }
  };

  useEffect(() => {
    autoComplete();
  }, [keyword]);

  useEffect(() => {
    function handleOutside(e) {
      if (autoRef.current && !autoRef.current.contains(e.target)) {
        setOver(false);
        setIndex(-1);
      }
    }
    document.addEventListener("mousedown", handleOutside);
    return () => {
      document.removeEventListener("mousedown", handleOutside);
    };
  }, [autoRef]);

  return (
    <>
      <div className="search">
        <div className="searchInner">
          <input
            ref={inputRef}
            type="text"
            size="30"
            defaultValue={keyword}
            placeholder="검색어를 입력해주세요"
            onChange={(e) => {
              if (!over) setOver(true);
              onChangeData(e);
            }}
            onKeyDown={(e) => {
              handleKeyArrow(e);
            }}
            onFocus={(e) => {
              setOver(true);
            }}
          />

          <div onClick={searchQuery}>
            <img src="/common/main_search.png" alt="" />
          </div>
        </div>
        {keyword?.length > 0 && over ? (
          <ul className="abs bk" ref={autoRef}>
            {data?.autoKeyword.length > 0 ? (
              data?.autoKeyword?.map((productName, idx) => {
                return productName.includes(keyword) ? (
                  <li
                    key={idx}
                    className={index === idx ? "autoName isFocus" : "autoName"}
                    onClick={() => {
                      searchClick(productName);
                    }}
                    onMouseEnter={() => setIndex(idx)}
                    onMouseLeave={() => setIndex(-1)}
                  >
                    <div className="name">
                      {productName.split(keyword)[0]}
                      <span className="boldName">{keyword}</span>
                      {productName.split(keyword)[1]}
                    </div>
                  </li>
                ) : (
                  <li
                    key={idx}
                    className={index === idx ? "autoName isFocus" : "autoName"}
                    onClick={() => {
                      searchClick(productName);
                      setOver(false);
                    }}
                    onMouseEnter={() => setIndex(idx)}
                    onMouseLeave={() => setIndex(-1)}
                  >
                    <div className="name">{productName}</div>
                  </li>
                );
              })
            ) : (
              <li className="autoName">검색결과가 없습니다.</li>
            )}
          </ul>
        ) : (
          ""
        )}
        <style jsx>
          {`
            .search {
              z-index: 999;
              position: absolute;
              top: 40px;
              left: 700px;
              background: #fff;
              border: 2px solid #ed1b23;

              .searchInner {
                display: flex;
                align-items: center;
              }

              input[type="text"] {
                border: none;
                padding: 0;
                box-sizing: border-box;
                color: #c5c9cd;
                font-weight: 600;
              }

              input[type="text"]:focus {
                outline: none;
                color: #000;
              }
            }
            .isFocus {
              background: #e5e5e5;
              cursor: pointer;
            }
            .bk {
              width: 317px;
              margin: 0;
              padding: 0px;
            }

            .contour {
              border: 1px solid #ed1b23;
            }

            .name {
              padding: 2px 0px;
            }

            .autoName {
              list-style: none;
            }

            .boldName {
              color: #ed1b23;
              font-weight: bold;
            }

            @media screen and (min-width: 769px) {
              /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */

              .search {
                border-radius: 15px;
                padding: 0 17px;

                input[type="text"] {
                  width: 317px;
                  height: 45px;
                  line-height: 45px;
                  font-size: 1em;
                }

                img {
                  width: 24px;
                  height: 24px;
                }
              }
            }

            @media screen and (max-width: 768px) {
              /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
              .search {
                left: 33vw;
                top: 3vw;
                border-radius: 2vw;
                padding: 0 0.9vw;

                ul {
                  width: 17vw;
                  font-size: 1vw;
                  line-height: 3vw;
                }

                input[type="text"] {
                  width: 17vw;
                  height: 2.4vw;
                  line-height: 4.4vw;
                  font-size: 1vw;
                }

                img {
                  display: flex;
                  width: 1.3vw;
                  height: 1.3vw;
                  min-width: 9px;
                }
              }

              .bk {
                width: 317px;
              }

              .name {
                padding: 2px 0px;
              }
            }

            @media screen and (max-width: 480px) {
              /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
              .search {
                left: 130px;
                top: 20px;
                border-radius: 15px;
                padding: 5px 10px;

                ul {
                  width: 120px;
                  font-size: 0.5em;
                }

                input[type="text"] {
                  width: 120px;
                  height: 15px;
                  font-size: 0.5em;
                }

                img {
                  width: 16px;
                  height: 16px;
                  top: 5px;
                  left: 130px;
                }
              }

              .bk {
                width: 317px;
              }

              .name {
                padding: 2px 0px;
              }
            }
          `}
        </style>
      </div>
    </>
  );
}
