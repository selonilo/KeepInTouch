import { EnumPostType } from "../../enum/enum.post.type";

export interface PostModel {
    id: number;
    createdDate: Date;
    updatedDate: Date;
    createdBy: string;
    updatedBy: string;
    title: string;
    content: string;
    userId: number;
    viewNumber: number;
    likeNumber: number;
    postType: EnumPostType;
}
