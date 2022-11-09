import { Search } from "@mui/icons-material";
import Link from "next/link";
import { router } from "next/router";
import { useEffect } from "react";
import { useState } from "react";
import { fetchAutoData } from "../../apis/AutoComplete";

export default function SearchBar({value}) {
    const [keyword, setKeyword] = useState();
    const [resource, setResource] = useState();

    // function search(event) {
    //     router.push(
    //     {
    //         pathname: `/product`,
    //         query: { cat_id: 0, search: value },
    //     },
    //     `/product`,
    //     );
    //     //   setValue("");
    // }
  
    useEffect(() => {
        setKeyword(value);
        autoComplete();
    }, [value]);
    
    const autoComplete = async () => {
        console.log("first ",keyword);
        setResource(fetchAutoData(keyword));
    }

    if(!keyword) return;

    const data = resource.ProductNames.read();

        return (
            <>
                {<ul className="abs bk" onKeyDown={e => {console.log("keydown")}}>
                    {data.autoKeyword.length > 0 ? 
                    data?.autoKeyword?.map((productName) => {
                        return (
                            productName.includes(keyword) ?
                                <li className="autoName" onClick={(e) => Search(e)}>
                                    {productName.split(keyword)[0]}
                                    <span className="boldName">{keyword}</span>
                                    {productName.split(keyword)[1]}
                                </li>  
                                : <li className="autoName" onClick={(e) => Search(e)}>{productName}</li>
                                )
                            })
                        : <li className="autoName">최근 검색결과가 없습니다.</li>}
                 </ul>}

                <style jsx>{`
                    @media screen and (min-width: 769px) {
                     /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
                     .abs {
                        position: absolute;
                     }

                     .bk {
                        /* background: #fff; */
                        background: #eee;
                        width: 317px;
                        margin: 0;
                        padding: 10px;
                     }

                     .autoName {
                        list-style: none;
                     }

                     .autoName:hover {
                        background: #1a1a1a;
                     }

                     .boldName {
                            color:#ed1b23; 
                            font-weight:bold;
                        }

                    }

                    @media screen and (max-width: 768px) {
                    /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
                    }
                `}
                </style>  
            </>
        );
    };