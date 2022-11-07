export default function ButtonComp({ context, unvalid }) {
  return (
    <>
      <button className="btn" disabled={unvalid}>
        {context}
      </button>

      <style jsx>
        {`
          .btn:disabled {
            background: #dadada;
            color: white;
            border: none;
          }

          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .btn {
              width: 200px;
              height: 60px;
              padding: 0px;
              line-height: 58px;
              color: #fff;
              font-size: 18px;
              background: #ff3d44;
              border-radius: 8px;
              border: 1px solid #ff3d44;
              text-align: center;
              cursor: pointer;
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .btn {
              width: 25vw;
              height: 10vw;
              padding: 0px;
              line-height: 10vw;
              color: #fff;
              font-size: 3vw;
              background: #ff3d44;
              border-radius: 8px;
              border: 1px solid #ff3d44;
              text-align: center;
              cursor: pointer;
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .btn {
              width: 200px;
              height: 60px;
              padding: 0px;
              line-height: 58px;
              color: #fff;
              font-size: 18px;
              background: #ff3d44;
              border-radius: 8px;
              border: 1px solid #ff3d44;
              text-align: center;
              cursor: pointer;
            }
          }
        `}
      </style>
    </>
  );
}

ButtonComp.defaultProps = {
  unvalid: false,
};
