

package kg.geektech.postapplesson2.ui.posts;

import kg.geektech.postapplesson2.data.models.Post;

public interface OnItemClickListener {
            void onClick (Post post);
            void onLongClick (Post post, int position);

}
