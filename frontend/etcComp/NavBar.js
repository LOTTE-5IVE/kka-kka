import Link from "next/link";

export default function NavBar() {
  return (
    <nav>
      <div>
        <ul>
          <li>
            <Link href="/">
              <a>홈</a>
            </Link>
          </li>
          <li>
            <Link href="/productList">
              <a>전체</a>
            </Link>
          </li>
          <li>
            <Link href="/join">
              <a>회원가입</a>
            </Link>
          </li>
          <li>
            <Link href="/login">
              <a>로그인</a>
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}
